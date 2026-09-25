const express = require('express');
const sqlite3 = require('sqlite3').verbose();
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3000;
const dbDir = path.join(__dirname, 'data');
const dbFile = path.join(dbDir, 'parking.db');

fs.mkdirSync(dbDir, { recursive: true });

const db = new sqlite3.Database(dbFile);

function run(sql, params = []) {
  return new Promise((resolve, reject) => {
    db.run(sql, params, function (err) {
      if (err) return reject(err);
      resolve({ id: this.lastID, changes: this.changes });
    });
  });
}

function get(sql, params = []) {
  return new Promise((resolve, reject) => {
    db.get(sql, params, (err, row) => {
      if (err) return reject(err);
      resolve(row);
    });
  });
}

function all(sql, params = []) {
  return new Promise((resolve, reject) => {
    db.all(sql, params, (err, rows) => {
      if (err) return reject(err);
      resolve(rows);
    });
  });
}

async function initDatabase() {
  const schema = `
    CREATE TABLE IF NOT EXISTS empleado (
      Id_empleado INTEGER PRIMARY KEY AUTOINCREMENT,
      Nombre TEXT NOT NULL,
      Apellido TEXT NOT NULL,
      NombreUsuario TEXT NOT NULL UNIQUE,
      Contraseña TEXT NOT NULL
    );

    CREATE TABLE IF NOT EXISTS espacios_estacionamiento (
      Id_plaza INTEGER PRIMARY KEY AUTOINCREMENT,
      Nombre_plaza TEXT NOT NULL UNIQUE,
      Estado TEXT NOT NULL CHECK(Estado IN ('Ocupado','Libre'))
    );

    CREATE TABLE IF NOT EXISTS vehiculo (
      Id_vehiculo INTEGER PRIMARY KEY AUTOINCREMENT,
      Placa TEXT NOT NULL UNIQUE,
      Propietario TEXT NOT NULL,
      Condicion TEXT,
      Tipo_vehiculo TEXT NOT NULL
    );

    CREATE TABLE IF NOT EXISTS registro_entrada (
      Id_registro INTEGER PRIMARY KEY AUTOINCREMENT,
      Id_vehiculo INTEGER NOT NULL,
      Fecha TEXT NOT NULL,
      Hora_entrada TEXT NOT NULL,
      Ubicacion TEXT NOT NULL,
      FOREIGN KEY (Id_vehiculo) REFERENCES vehiculo(Id_vehiculo)
    );

    CREATE TABLE IF NOT EXISTS registro_salida (
      Id_registro INTEGER PRIMARY KEY AUTOINCREMENT,
      Id_vehiculo INTEGER NOT NULL,
      Fecha TEXT NOT NULL,
      Hora_salida TEXT NOT NULL,
      FOREIGN KEY (Id_vehiculo) REFERENCES vehiculo(Id_vehiculo)
    );

    CREATE TABLE IF NOT EXISTS reservas (
      Id_reserva INTEGER PRIMARY KEY AUTOINCREMENT,
      Id_plaza_estacionamiento INTEGER,
      Nombre_reservista TEXT NOT NULL,
      Fecha_reserva TEXT NOT NULL,
      Hora_inicio_reserva TEXT NOT NULL,
      Hora_fin_reserva TEXT NOT NULL,
      Estado_reserva TEXT NOT NULL CHECK(Estado_reserva IN ('Activa','Caducada','Cancelada'))
    );

    CREATE TABLE IF NOT EXISTS incidentes_problemas (
      Id_incidente INTEGER PRIMARY KEY AUTOINCREMENT,
      Id_vehiculo INTEGER NOT NULL,
      Descripcion TEXT NOT NULL,
      Fecha_incidente TEXT NOT NULL,
      Hora_incidente TEXT NOT NULL,
      FOREIGN KEY (Id_vehiculo) REFERENCES vehiculo(Id_vehiculo)
    );

    CREATE TABLE IF NOT EXISTS ticket (
      Cod_Factura INTEGER PRIMARY KEY AUTOINCREMENT,
      Fecha TEXT NOT NULL,
      Fecha_entrada TEXT NOT NULL,
      Fecha_salida TEXT NOT NULL,
      Importe REAL NOT NULL,
      Id_incidente INTEGER,
      Id_vehiculo INTEGER NOT NULL,
      FOREIGN KEY (Id_vehiculo) REFERENCES vehiculo(Id_vehiculo)
    );
  `;

  await new Promise((resolve, reject) => {
    db.exec(schema, (err) => {
      if (err) return reject(err);
      resolve();
    });
  });

  const espacios = [
    'A-01', 'A-02', 'A-03', 'A-04',
    'B-01', 'B-02', 'B-03', 'B-04',
    'C-01', 'C-02', 'C-03', 'C-04',
    'D-01', 'D-02', 'D-03', 'D-04'
  ];

  const count = await get('SELECT COUNT(*) AS total FROM espacios_estacionamiento');
  if (count.total === 0) {
    for (const plaza of espacios) {
      await run('INSERT INTO espacios_estacionamiento (Nombre_plaza, Estado) VALUES (?, ?)', [plaza, 'Libre']);
    }
  }

  const admin = await get('SELECT 1 FROM empleado WHERE NombreUsuario = ?', ['admin']);
  if (!admin) {
    await run(
      'INSERT INTO empleado (Nombre, Apellido, NombreUsuario, Contraseña) VALUES (?, ?, ?, ?)',
      ['Admin', 'Sistema', 'admin', '1234']
    );
  }
}

function calcularImporte(fechaEntrada, horaEntrada, fechaSalida, horaSalida) {
  const inicio = new Date(`${fechaEntrada}T${horaEntrada}`);
  const fin = new Date(`${fechaSalida}T${horaSalida}`);
  const diffMs = fin.getTime() - inicio.getTime();
  if (Number.isNaN(diffMs) || diffMs <= 0) return 0;

  const horas = diffMs / (1000 * 60 * 60);
  const tarifaPorHora = 2000;
  const tarifaPorDia = 8000;

  let total = horas * tarifaPorHora;
  if (horas >= 24) {
    const dias = Math.floor(horas / 24);
    const remanente = horas % 24;
    total = (dias * tarifaPorDia) + (remanente * tarifaPorHora);
  }

  return Number(total.toFixed(2));
}

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use('/images', express.static(path.join(__dirname, 'src', 'IMAGENES')));
app.use(express.static(path.join(__dirname, 'public')));

app.get('/api/health', (req, res) => {
  res.json({ ok: true, message: 'API de parqueadero funcionando' });
});

app.post('/api/login', async (req, res) => {
  const { usuario, contrasena } = req.body;

  if (!usuario || !contrasena) {
    return res.status(400).json({ ok: false, message: 'Usuario y contraseña son requeridos.' });
  }

  try {
    const empleado = await get(
      'SELECT * FROM empleado WHERE NombreUsuario = ? AND Contraseña = ?',
      [usuario, contrasena]
    );

    if (!empleado) {
      return res.status(401).json({ ok: false, message: 'Credenciales incorrectas.' });
    }

    return res.json({ ok: true, message: 'Inicio de sesión correcto.', usuario: empleado.NombreUsuario });
  } catch (error) {
    return res.status(500).json({ ok: false, message: 'Error al iniciar sesión.', error: error.message });
  }
});

app.post('/api/register', async (req, res) => {
  const { nombre, apellido, usuario, contrasena } = req.body;

  if (!nombre || !apellido || !usuario || !contrasena) {
    return res.status(400).json({ ok: false, message: 'Todos los campos son obligatorios.' });
  }

  try {
    const existe = await get('SELECT 1 FROM empleado WHERE NombreUsuario = ?', [usuario]);
    if (existe) {
      return res.status(409).json({ ok: false, message: 'El usuario ya existe.' });
    }

    await run(
      'INSERT INTO empleado (Nombre, Apellido, NombreUsuario, Contraseña) VALUES (?, ?, ?, ?)',
      [nombre, apellido, usuario, contrasena]
    );

    return res.json({ ok: true, message: 'Usuario registrado con éxito.' });
  } catch (error) {
    return res.status(500).json({ ok: false, message: 'Error al registrar.', error: error.message });
  }
});

app.get('/api/espacios', async (req, res) => {
  try {
    const espacios = await all('SELECT * FROM espacios_estacionamiento ORDER BY Id_plaza');
    res.json({ ok: true, data: espacios });
  } catch (error) {
    res.status(500).json({ ok: false, message: error.message });
  }
});

app.post('/api/vehiculos/ingresar', async (req, res) => {
  const {
    placa,
    propietario,
    condicion,
    tipoVehiculo,
    fecha,
    hora,
    ubicacion
  } = req.body;

  if (!placa || !propietario || !tipoVehiculo || !fecha || !hora || !ubicacion) {
    return res.status(400).json({ ok: false, message: 'Faltan datos del vehículo.' });
  }

  try {
    const existente = await get('SELECT Id_vehiculo FROM vehiculo WHERE Placa = ?', [placa]);
    if (existente) {
      return res.status(409).json({ ok: false, message: 'La placa ya existe en el sistema.' });
    }

    const vehiculo = await run(
      'INSERT INTO vehiculo (Placa, Propietario, Condicion, Tipo_vehiculo) VALUES (?, ?, ?, ?)',
      [placa, propietario, condicion || '', tipoVehiculo]
    );

    const idVehiculo = vehiculo.id;

    await run(
      'INSERT INTO registro_entrada (Id_vehiculo, Fecha, Hora_entrada, Ubicacion) VALUES (?, ?, ?, ?)',
      [idVehiculo, fecha, hora, ubicacion]
    );

    await run('UPDATE espacios_estacionamiento SET Estado = ? WHERE Nombre_plaza = ?', ['Ocupado', ubicacion]);

    res.json({ ok: true, message: 'Vehículo registrado con éxito.' });
  } catch (error) {
    res.status(500).json({ ok: false, message: 'No se pudo registrar el vehículo.', error: error.message });
  }
});

app.get('/api/vehiculos', async (req, res) => {
  const { placa, propietario, fecha, tipoVehiculo, ubicacion } = req.query;

  try {
    let query = `
      SELECT v.Id_vehiculo, v.Placa, v.Propietario, v.Tipo_vehiculo,
             re.Fecha AS Fecha_entrada, re.Hora_entrada,
             rs.Fecha AS Fecha_salida, rs.Hora_salida,
             t.Importe
      FROM vehiculo v
      LEFT JOIN registro_entrada re ON v.Id_vehiculo = re.Id_vehiculo
      LEFT JOIN registro_salida rs ON v.Id_vehiculo = rs.Id_vehiculo
      LEFT JOIN ticket t ON v.Id_vehiculo = t.Id_vehiculo
      WHERE 1 = 1
    `;

    const params = [];

    if (placa) {
      query += ' AND v.Placa = ?';
      params.push(placa);
    }

    if (propietario) {
      query += ' AND v.Propietario = ?';
      params.push(propietario);
    }

    if (fecha) {
      query += ' AND re.Fecha = ?';
      params.push(fecha);
    }

    if (tipoVehiculo) {
      query += ' AND v.Tipo_vehiculo = ?';
      params.push(tipoVehiculo);
    }

    if (ubicacion) {
      query += ' AND re.Ubicacion = ?';
      params.push(ubicacion);
    }

    query += ' ORDER BY v.Id_vehiculo DESC';

    const rows = await all(query, params);
    res.json({ ok: true, data: rows });
  } catch (error) {
    res.status(500).json({ ok: false, message: error.message });
  }
});

app.post('/api/vehiculos/salir', async (req, res) => {
  const { placa } = req.body;

  if (!placa) {
    return res.status(400).json({ ok: false, message: 'Digite la placa para retirar el vehículo.' });
  }

  try {
    const vehiculo = await get('SELECT Id_vehiculo FROM vehiculo WHERE Placa = ?', [placa]);
    if (!vehiculo) {
      return res.status(404).json({ ok: false, message: 'La placa no existe en el sistema.' });
    }

    const entrada = await get(
      'SELECT * FROM registro_entrada WHERE Id_vehiculo = ? ORDER BY Id_registro DESC LIMIT 1',
      [vehiculo.Id_vehiculo]
    );

    if (!entrada) {
      return res.status(404).json({ ok: false, message: 'No hay registro de entrada para ese vehículo.' });
    }

    const fechaActual = new Date().toISOString().slice(0, 10);
    const horaActual = new Date().toTimeString().slice(0, 8);

    await run(
      'INSERT INTO registro_salida (Id_vehiculo, Fecha, Hora_salida) VALUES (?, ?, ?)',
      [vehiculo.Id_vehiculo, fechaActual, horaActual]
    );

    const incidente = await get(
      'SELECT Descripcion FROM incidentes_problemas WHERE Id_vehiculo = ? ORDER BY Id_incidente DESC LIMIT 1',
      [vehiculo.Id_vehiculo]
    );

    const importe = calcularImporte(entrada.Fecha, entrada.Hora_entrada, fechaActual, horaActual);

    await run(
      'INSERT INTO ticket (Fecha, Fecha_entrada, Fecha_salida, Importe, Id_incidente, Id_vehiculo) VALUES (?, ?, ?, ?, ?, ?)',
      [fechaActual, `${entrada.Fecha} ${entrada.Hora_entrada}`, `${fechaActual} ${horaActual}`, importe, incidente ? 1 : null, vehiculo.Id_vehiculo]
    );

    const idIncidente = incidente ? (await get('SELECT Id_incidente FROM incidentes_problemas WHERE Id_vehiculo = ? ORDER BY Id_incidente DESC LIMIT 1', [vehiculo.Id_vehiculo])).Id_incidente : null;
    if (idIncidente) {
      await run('UPDATE ticket SET Id_incidente = ? WHERE Id_vehiculo = ? AND Fecha = ?', [idIncidente, vehiculo.Id_vehiculo, fechaActual]);
    }

    await run('UPDATE espacios_estacionamiento SET Estado = ? WHERE Nombre_plaza = ?', ['Libre', entrada.Ubicacion]);

    const factura = await get(
      'SELECT * FROM ticket WHERE Id_vehiculo = ? ORDER BY Cod_Factura DESC LIMIT 1',
      [vehiculo.Id_vehiculo]
    );

    res.json({
      ok: true,
      message: 'Vehículo retirado con éxito.',
      factura,
      novedad: incidente ? incidente.Descripcion : 'SIN NOVEDAD'
    });
  } catch (error) {
    res.status(500).json({ ok: false, message: 'Error al retirar el vehículo.', error: error.message });
  }
});

app.get('/api/vehiculos/:placa/entrada', async (req, res) => {
  const { placa } = req.params;

  try {
    const vehiculo = await get('SELECT Id_vehiculo FROM vehiculo WHERE Placa = ?', [placa]);
    if (!vehiculo) {
      return res.status(404).json({ ok: false, message: 'La placa no existe.' });
    }

    const entrada = await get(
      'SELECT Fecha, Hora_entrada, Ubicacion FROM registro_entrada WHERE Id_vehiculo = ? ORDER BY Id_registro DESC LIMIT 1',
      [vehiculo.Id_vehiculo]
    );

    const incidente = await get(
      'SELECT Descripcion FROM incidentes_problemas WHERE Id_vehiculo = ? ORDER BY Id_incidente DESC LIMIT 1',
      [vehiculo.Id_vehiculo]
    );

    res.json({ ok: true, data: { ...entrada, novedad: incidente ? incidente.Descripcion : 'SIN NOVEDAD' } });
  } catch (error) {
    res.status(500).json({ ok: false, message: error.message });
  }
});

app.get('/api/reservas', async (req, res) => {
  try {
    const reservas = await all('SELECT * FROM reservas ORDER BY Id_reserva DESC');
    res.json({ ok: true, data: reservas });
  } catch (error) {
    res.status(500).json({ ok: false, message: error.message });
  }
});

app.post('/api/reservas', async (req, res) => {
  const { propietario, ubicacion, fecha, horaInicio, horaFin } = req.body;

  if (!propietario || !ubicacion || !fecha || !horaInicio || !horaFin) {
    return res.status(400).json({ ok: false, message: 'Todos los campos de reserva son obligatorios.' });
  }

  try {
    const espacio = await get('SELECT Id_plaza FROM espacios_estacionamiento WHERE Nombre_plaza = ?', [ubicacion]);
    const estado = new Date(`${fecha}T${horaFin}`) < new Date() ? 'Caducada' : 'Activa';

    await run(
      'INSERT INTO reservas (Id_plaza_estacionamiento, Nombre_reservista, Fecha_reserva, Hora_inicio_reserva, Hora_fin_reserva, Estado_reserva) VALUES (?, ?, ?, ?, ?, ?)',
      [espacio ? espacio.Id_plaza : null, propietario, fecha, horaInicio, horaFin, estado]
    );

    if (estado === 'Activa') {
      await run('UPDATE espacios_estacionamiento SET Estado = ? WHERE Nombre_plaza = ?', ['Ocupado', ubicacion]);
    }

    res.json({ ok: true, message: 'Reserva realizada con éxito.' });
  } catch (error) {
    res.status(500).json({ ok: false, message: 'No se pudo guardar la reserva.', error: error.message });
  }
});

app.post('/api/incidentes', async (req, res) => {
  const { placa, fecha, hora, descripcion } = req.body;

  if (!placa || !fecha || !hora || !descripcion) {
    return res.status(400).json({ ok: false, message: 'Todos los campos del incidente son obligatorios.' });
  }

  try {
    const vehiculo = await get('SELECT Id_vehiculo FROM vehiculo WHERE Placa = ?', [placa]);
    if (!vehiculo) {
      return res.status(404).json({ ok: false, message: 'La placa no se encuentra registrada en el sistema.' });
    }

    await run(
      'INSERT INTO incidentes_problemas (Id_vehiculo, Descripcion, Fecha_incidente, Hora_incidente) VALUES (?, ?, ?, ?)',
      [vehiculo.Id_vehiculo, descripcion, fecha, hora]
    );

    res.json({ ok: true, message: 'Incidente registrado con éxito.' });
  } catch (error) {
    res.status(500).json({ ok: false, message: 'No se pudo registrar el incidente.', error: error.message });
  }
});

app.get('/api/ticket/:placa', async (req, res) => {
  const { placa } = req.params;

  try {
    const vehiculo = await get('SELECT Id_vehiculo, Propietario, Tipo_vehiculo FROM vehiculo WHERE Placa = ?', [placa]);
    if (!vehiculo) {
      return res.status(404).json({ ok: false, message: 'No existe un ticket para esa placa.' });
    }

    const ultimoTicket = await get(
      'SELECT * FROM ticket WHERE Id_vehiculo = ? ORDER BY Cod_Factura DESC LIMIT 1',
      [vehiculo.Id_vehiculo]
    );

    if (!ultimoTicket) {
      return res.status(404).json({ ok: false, message: 'No hay ticket generado.' });
    }

    res.json({ ok: true, data: { ...ultimoTicket, ...vehiculo } });
  } catch (error) {
    res.status(500).json({ ok: false, message: error.message });
  }
});

app.get(/^(?!\/api).*/, (req, res) => {
  const filePath = path.join(__dirname, 'public', req.path === '/' ? 'index.html' : req.path);
  if (filePath.endsWith('.html')) {
    return res.sendFile(filePath);
  }
  return res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

async function startServer() {
  await initDatabase();

  app.listen(PORT, () => {
    console.log(`Servidor de parqueadero corriendo en http://localhost:${PORT}`);
  });
}

startServer().catch((error) => {
  console.error('Error al iniciar la aplicación:', error);
  process.exit(1);
});

process.on('SIGINT', () => {
  db.close();
  process.exit(0);
});
