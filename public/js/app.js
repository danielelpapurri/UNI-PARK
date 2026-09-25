const STORAGE_KEY = 'uniParkAuth';
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
    const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
    const response = await fetch(url, { ...options, headers });
    const data = await response.json().catch(() => ({}));

    if (!response.ok) {
      throw new Error(data.message || 'Error en la petición');
    }

    return data;
  }
};

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

  if (PROTECTED_PATHS.includes(currentPath) && !isLoggedIn()) {
    window.location.href = '/pages/login.html';
    return false;
  }

  if ((currentPath === '/' || currentPath === '/index.html') && isLoggedIn()) {
    window.location.href = '/pages/menu.html';
    return false;
  }

  if ((currentPath === '/pages/login.html' || currentPath === '/pages/register.html') && isLoggedIn()) {
    window.location.href = '/pages/menu.html';
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
    setTimeout(() => window.location.href = '/pages/menu.html', 700);
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
    setTimeout(() => window.location.href = '/pages/login.html', 900);
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
      window.location.href = '/pages/login.html';
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
    loadReservations();
  }

  const incidentForm = document.getElementById('incidentForm');
  if (incidentForm) incidentForm.addEventListener('submit', handleIncident);
}

document.addEventListener('DOMContentLoaded', initGeneralPages);
