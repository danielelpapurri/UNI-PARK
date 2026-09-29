const STORAGE_KEY = 'uniParkAuth';
const DATA_KEY = 'uniParkData';
const DEFAULT_SPACES = [
  'A-01', 'A-02', 'A-03', 'A-04',
  'B-01', 'B-02', 'B-03', 'B-04',
  'C-01', 'C-02', 'C-03', 'C-04',
  'D-01', 'D-02', 'D-03', 'D-04'
];
const PROTECTED_PATHS = [
  '/pages/menu.html',
  '/pages/ingresar-vehiculo.html',
  '/pages/retirar-vehiculo.html',
  '/pages/listar-vehiculos.html',
  '/pages/reservas.html',
  '/pages/incidentes.html',
  '/pages/visualizar-reservas.html'
];

const API = {
  async request(url, options = {}) {
    const requestUrl = new URL(url, window.location.href);
    const method = options.method || 'GET';
    const payload = options.body ? JSON.parse(options.body) : {};
    const data = getData();
    let result;

    if (requestUrl.pathname === '/api/login' && method === 'POST') {
      const user = data.users.find((item) => item.usuario === payload.usuario && item.contrasena === payload.contrasena);
      if (!user) throw new Error('Credenciales incorrectas.');
      result = { ok: true, message: 'Inicio de sesión correcto.', usuario: user.usuario };
    } else if (requestUrl.pathname === '/api/register' && method === 'POST') {
      if (data.users.some((item) => item.usuario === payload.usuario)) throw new Error('El usuario ya existe.');
      data.users.push(payload);
      result = { ok: true, message: 'Usuario registrado con éxito.' };
    } else if (requestUrl.pathname === '/api/espacios') {
      result = { ok: true, data: data.spaces };
    } else if (requestUrl.pathname === '/api/vehiculos/ingresar' && method === 'POST') {
      const space = data.spaces.find((item) => item.Nombre_plaza === payload.ubicacion);
      if (data.vehicles.some((item) => item.Placa === payload.placa)) throw new Error('La placa ya existe en el sistema.');
      if (!space || space.Estado !== 'Libre') throw new Error('La plaza seleccionada no está disponible.');
      data.vehicles.push({
        Id_vehiculo: nextId(data.vehicles, 'Id_vehiculo'),
        Placa: payload.placa,
        Propietario: payload.propietario,
        Condicion: payload.condicion,
        Tipo_vehiculo: payload.tipoVehiculo,
        Fecha_entrada: payload.fecha,
        Hora_entrada: payload.hora,
        Ubicacion: payload.ubicacion,
        Fecha_salida: '',
        Hora_salida: '',
        Importe: ''
      });
      space.Estado = 'Ocupado';
      result = { ok: true, message: 'Vehículo registrado con éxito.' };
    } else if (requestUrl.pathname === '/api/vehiculos' && method === 'GET') {
      const filters = new URLSearchParams(requestUrl.search);
      const rows = data.vehicles.filter((vehicle) =>
        (!filters.get('placa') || vehicle.Placa === filters.get('placa')) &&
        (!filters.get('propietario') || vehicle.Propietario === filters.get('propietario')) &&
        (!filters.get('fecha') || vehicle.Fecha_entrada === filters.get('fecha')) &&
        (!filters.get('tipoVehiculo') || vehicle.Tipo_vehiculo === filters.get('tipoVehiculo')) &&
        (!filters.get('ubicacion') || vehicle.Ubicacion === filters.get('ubicacion'))
      ).sort((first, second) => second.Id_vehiculo - first.Id_vehiculo);
      result = { ok: true, data: rows };
    } else if (requestUrl.pathname === '/api/vehiculos/salir' && method === 'POST') {
      const vehicle = data.vehicles.find((item) => item.Placa === payload.placa && !item.Fecha_salida);
      if (!vehicle) throw new Error('No hay un vehículo activo con esa placa.');
      const now = new Date();
      const fechaSalida = now.toISOString().slice(0, 10);
      const horaSalida = now.toTimeString().slice(0, 8);
      vehicle.Fecha_salida = fechaSalida;
      vehicle.Hora_salida = horaSalida;
      vehicle.Importe = calculateFee(vehicle.Fecha_entrada, vehicle.Hora_entrada, fechaSalida, horaSalida);
      const space = data.spaces.find((item) => item.Nombre_plaza === vehicle.Ubicacion);
      if (space) space.Estado = 'Libre';
      const incident = data.incidents.filter((item) => item.placa === vehicle.Placa).at(-1);
      result = {
        ok: true,
        message: 'Vehículo retirado con éxito.',
        factura: { Fecha_entrada: vehicle.Fecha_entrada, Fecha_salida: fechaSalida, Importe: vehicle.Importe },
        novedad: incident ? incident.descripcion : 'SIN NOVEDAD'
      };
    } else if (requestUrl.pathname.startsWith('/api/vehiculos/') && requestUrl.pathname.endsWith('/entrada')) {
      const placa = decodeURIComponent(requestUrl.pathname.split('/')[3]);
      const vehicle = data.vehicles.find((item) => item.Placa === placa && !item.Fecha_salida);
      if (!vehicle) throw new Error('La placa no existe o no tiene un ingreso activo.');
      const incident = data.incidents.filter((item) => item.placa === placa).at(-1);
      result = {
        ok: true,
        data: {
          Fecha: vehicle.Fecha_entrada,
          Hora_entrada: vehicle.Hora_entrada,
          novedad: incident ? incident.descripcion : 'SIN NOVEDAD'
        }
      };
    } else if (requestUrl.pathname === '/api/reservas' && method === 'GET') {
      result = { ok: true, data: [...data.reservations].reverse() };
    } else if (requestUrl.pathname === '/api/reservas' && method === 'POST') {
      const status = new Date(`${payload.fecha}T${payload.horaFin}`) < new Date() ? 'Caducada' : 'Activa';
      data.reservations.push({
        Id_reserva: nextId(data.reservations, 'Id_reserva'),
        Nombre_reservista: payload.propietario,
        Fecha_reserva: payload.fecha,
        Hora_inicio_reserva: payload.horaInicio,
        Hora_fin_reserva: payload.horaFin,
        Estado_reserva: status
      });
      result = { ok: true, message: 'Reserva realizada con éxito.' };
    } else if (requestUrl.pathname === '/api/incidentes' && method === 'POST') {
      if (!data.vehicles.some((item) => item.Placa === payload.placa)) throw new Error('La placa no existe en el sistema.');
      data.incidents.push(payload);
      result = { ok: true, message: 'Incidente registrado con éxito.' };
    } else {
      throw new Error('Operación no disponible en esta página.');
    }

    saveData(data);
    return result;
  }
};

function getData() {
  const saved = localStorage.getItem(DATA_KEY);
  if (saved) return JSON.parse(saved);

  return {
    users: [{ nombre: 'Admin', apellido: 'Sistema', usuario: 'admin', contrasena: '1234' }],
    spaces: DEFAULT_SPACES.map((name, index) => ({ Id_plaza: index + 1, Nombre_plaza: name, Estado: 'Libre' })),
    vehicles: [],
    reservations: [],
    incidents: []
  };
}

function saveData(data) {
  localStorage.setItem(DATA_KEY, JSON.stringify(data));
}

function nextId(items, key) {
  return items.reduce((largest, item) => Math.max(largest, item[key]), 0) + 1;
}

function calculateFee(entryDate, entryTime, exitDate, exitTime) {
  const elapsedHours = (new Date(`${exitDate}T${exitTime}`) - new Date(`${entryDate}T${entryTime}`)) / 3600000;
  if (!Number.isFinite(elapsedHours) || elapsedHours <= 0) return 0;
  const amount = elapsedHours >= 24
    ? Math.floor(elapsedHours / 24) * 8000 + (elapsedHours % 24) * 2000
    : elapsedHours * 2000;
  return Number(amount.toFixed(2));
}

function isLoggedIn() {
  return Boolean(localStorage.getItem(STORAGE_KEY));
}

function setSession(user) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify({ user }));
}

function clearSession() {
  localStorage.removeItem(STORAGE_KEY);
}

function requireAuth() {
  const currentPath = window.location.pathname;

  if (PROTECTED_PATHS.some((path) => currentPath.endsWith(path)) && !isLoggedIn()) {
    window.location.href = 'login.html';
    return false;
  }

  if ((currentPath === '/' || currentPath.endsWith('/index.html')) && isLoggedIn()) {
    window.location.href = 'pages/menu.html';
    return false;
  }

  if ((currentPath.endsWith('/pages/login.html') || currentPath.endsWith('/pages/register.html')) && isLoggedIn()) {
    window.location.href = 'menu.html';
    return false;
  }

  return true;
}

function showAlert(message, type = 'success') {
  const alertBox = document.getElementById('alertBox');
  if (!alertBox) return;

  alertBox.textContent = message;
  alertBox.className = `alert show ${type}`;
}

function clearAlert() {
  const alertBox = document.getElementById('alertBox');
  if (alertBox) {
    alertBox.className = 'alert';
    alertBox.textContent = '';
  }
}

function setCurrentDate() {
  const today = new Date();
  const fecha = today.toISOString().slice(0, 10);
  const fechaInput = document.getElementById('fechaIngreso');
  if (fechaInput) fechaInput.value = fecha;
}

async function loadSpaces() {
  const ubicacion = document.getElementById('ubicacion');
  if (!ubicacion) return;

  try {
    const result = await API.request('/api/espacios');
    ubicacion.innerHTML = '<option value="">Seleccione una plaza</option>';
    result.data.forEach((item) => {
      if (item.Estado === 'Libre') {
        const option = document.createElement('option');
        option.value = item.Nombre_plaza;
        option.textContent = item.Nombre_plaza;
        ubicacion.appendChild(option);
      }
    });
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function loadReserveSpaces() {
  const select = document.getElementById('ubicacionReserva');
  if (!select) return;

  try {
    const result = await API.request('/api/espacios');
    select.innerHTML = '<option value="">Seleccione una plaza</option>';
    result.data.forEach((item) => {
      const option = document.createElement('option');
      option.value = item.Nombre_plaza;
      option.textContent = `${item.Nombre_plaza} (${item.Estado})`;
      select.appendChild(option);
    });
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function handleLogin(event) {
  event.preventDefault();
  clearAlert();

  const payload = {
    usuario: document.getElementById('usuario').value,
    contrasena: document.getElementById('contrasena').value
  };

  try {
    const result = await API.request('/api/login', {
      method: 'POST',
      body: JSON.stringify(payload)
    });

    setSession(payload.usuario);
    showAlert(result.message, 'success');
    setTimeout(() => window.location.href = 'menu.html', 700);
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function handleRegister(event) {
  event.preventDefault();
  clearAlert();

  const payload = {
    nombre: document.getElementById('nombre').value,
    apellido: document.getElementById('apellido').value,
    usuario: document.getElementById('usuario').value,
    contrasena: document.getElementById('contrasena').value
  };

  try {
    const result = await API.request('/api/register', {
      method: 'POST',
      body: JSON.stringify(payload)
    });

    showAlert(result.message, 'success');
    setTimeout(() => window.location.href = 'login.html', 900);
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function handleVehicleEntry(event) {
  event.preventDefault();
  clearAlert();

  const payload = {
    placa: document.getElementById('placa').value,
    propietario: document.getElementById('propietario').value,
    condicion: document.getElementById('condicion').value,
    tipoVehiculo: document.getElementById('tipoVehiculo').value,
    fecha: document.getElementById('fechaIngreso').value,
    hora: document.getElementById('horaEntrada').value,
    ubicacion: document.getElementById('ubicacion').value
  };

  try {
    const result = await API.request('/api/vehiculos/ingresar', {
      method: 'POST',
      body: JSON.stringify(payload)
    });

    showAlert(result.message, 'success');
    event.target.reset();
    setCurrentDate();
    loadSpaces();
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function handleVehicleExit(event) {
  event.preventDefault();
  clearAlert();

  const placa = document.getElementById('placaSalida').value;
  try {
    const result = await API.request('/api/vehiculos/salir', {
      method: 'POST',
      body: JSON.stringify({ placa })
    });

    document.getElementById('fechaEntrada').value = result.factura ? result.factura.Fecha_entrada : '';
    document.getElementById('fechaSalida').value = result.factura ? result.factura.Fecha_salida : '';
    document.getElementById('novedades').value = result.novedad || 'SIN NOVEDAD';
    document.getElementById('importe').value = result.factura ? result.factura.Importe : '';
    showAlert(result.message, 'success');
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function fetchVehicleInfo() {
  const placa = document.getElementById('placaSalida').value;
  if (!placa) return;

  try {
    const result = await API.request(`/api/vehiculos/${placa}/entrada`);
    document.getElementById('fechaEntrada').value = result.data.Fecha || '';
    document.getElementById('horaEntrada').value = result.data.Hora_entrada || '';
    document.getElementById('novedades').value = result.data.novedad || 'SIN NOVEDAD';
    document.getElementById('fechaSalida').value = new Date().toISOString().slice(0, 10);
  } catch (error) {
    document.getElementById('novedades').value = 'SIN NOVEDAD';
    document.getElementById('fechaEntrada').value = '';
    document.getElementById('horaEntrada').value = '';
    document.getElementById('fechaSalida').value = new Date().toISOString().slice(0, 10);
  }
}

async function loadVehicleTable() {
  const tableBody = document.getElementById('tblVehiculosBody');
  if (!tableBody) return;

  const filters = {
    placa: document.getElementById('filterPlaca')?.value || '',
    propietario: document.getElementById('filterPropietario')?.value || '',
    fecha: document.getElementById('filterFecha')?.value || '',
    tipoVehiculo: document.getElementById('filterTipo')?.value || '',
    ubicacion: document.getElementById('filterUbicacion')?.value || ''
  };

  const query = new URLSearchParams(filters).toString();

  try {
    const result = await API.request(`/api/vehiculos?${query}`);
    tableBody.innerHTML = '';

    result.data.forEach((row) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${row.Id_vehiculo}</td>
        <td>${row.Placa}</td>
        <td>${row.Propietario}</td>
        <td>${row.Tipo_vehiculo}</td>
        <td>${row.Fecha_entrada || ''} ${row.Hora_entrada || ''}</td>
        <td>${row.Fecha_salida || ''} ${row.Hora_salida || ''}</td>
        <td>${row.Importe || '0'}</td>
      `;
      tableBody.appendChild(tr);
    });
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function handleReservation(event) {
  event.preventDefault();
  clearAlert();

  const payload = {
    propietario: document.getElementById('propietarioReserva').value,
    ubicacion: document.getElementById('ubicacionReserva').value,
    fecha: document.getElementById('fechaReserva').value,
    horaInicio: document.getElementById('horaInicio').value,
    horaFin: document.getElementById('horaFin').value
  };

  try {
    const result = await API.request('/api/reservas', {
      method: 'POST',
      body: JSON.stringify(payload)
    });

    showAlert(result.message, 'success');
    event.target.reset();
    loadReserveSpaces();
    loadReservations();
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function loadReservations() {
  const tableBody = document.getElementById('tblReservasBody');
  if (!tableBody) return;

  try {
    const result = await API.request('/api/reservas');
    tableBody.innerHTML = '';

    result.data.forEach((row) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${row.Id_reserva}</td>
        <td>${row.Nombre_reservista}</td>
        <td>${row.Fecha_reserva}</td>
        <td>${row.Hora_inicio_reserva}</td>
        <td>${row.Hora_fin_reserva}</td>
        <td>${row.Estado_reserva}</td>
      `;
      tableBody.appendChild(tr);
    });
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

async function handleIncident(event) {
  event.preventDefault();
  clearAlert();

  const payload = {
    placa: document.getElementById('placaIncidente').value,
    fecha: document.getElementById('fechaIncidente').value,
    hora: document.getElementById('horaIncidente').value,
    descripcion: document.getElementById('descripcionIncidente').value
  };

  try {
    const result = await API.request('/api/incidentes', {
      method: 'POST',
      body: JSON.stringify(payload)
    });

    showAlert(result.message, 'success');
    event.target.reset();
  } catch (error) {
    showAlert(error.message, 'error');
  }
}

function initGeneralPages() {
  requireAuth();

  const moduleList = document.getElementById('systemModules');
  if (moduleList) {
    if (isLoggedIn()) {
      moduleList.style.display = 'block';
    } else {
      moduleList.innerHTML = '<li>Debes iniciar sesión para ver el contenido del sistema.</li>';
    }
  }

  const logoutBtn = document.getElementById('logoutBtn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      clearSession();
      window.location.href = 'login.html';
    });
  }

  const loginForm = document.getElementById('loginForm');
  if (loginForm) loginForm.addEventListener('submit', handleLogin);

  const registerForm = document.getElementById('registerForm');
  if (registerForm) registerForm.addEventListener('submit', handleRegister);

  const vehicleForm = document.getElementById('vehicleForm');
  if (vehicleForm) {
    vehicleForm.addEventListener('submit', handleVehicleEntry);
    setCurrentDate();
    loadSpaces();
  }

  const exitForm = document.getElementById('exitForm');
  if (exitForm) {
    exitForm.addEventListener('submit', handleVehicleExit);
    document.getElementById('placaSalida').addEventListener('keyup', fetchVehicleInfo);
    document.getElementById('fechaSalida').value = new Date().toISOString().slice(0, 10);
  }

  const searchBtn = document.getElementById('searchVehicles');
  if (searchBtn) searchBtn.addEventListener('click', loadVehicleTable);

  const reservationForm = document.getElementById('reservationForm');
  if (reservationForm) {
    reservationForm.addEventListener('submit', handleReservation);
    loadReserveSpaces();
  }

  if (document.getElementById('tblReservasBody')) loadReservations();

  const incidentForm = document.getElementById('incidentForm');
  if (incidentForm) incidentForm.addEventListener('submit', handleIncident);
}

document.addEventListener('DOMContentLoaded', initGeneralPages);
