const express = require('express');
const bodyParser = require('body-parser');
const bcrypt = require('bcryptjs');
const { Pool } = require('pg');

const app = express();
const port = Number(process.env.PORT) || 3000;
const databaseUrl = process.env.DATABASE_URL;

const pool = new Pool({
  connectionString: databaseUrl,
  ssl: process.env.NODE_ENV === 'production' ? { rejectUnauthorized: false } : false
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.get('/', (req, res) => {
  res.json({ ok: true, message: 'UNI-PARK API funcionando.' });
});

app.post('/api/register', async (req, res) => {
  const nombre = typeof req.body.nombre === 'string' ? req.body.nombre.trim() : '';
  const email = typeof req.body.email === 'string' ? req.body.email.trim().toLowerCase() : '';
  const password = typeof req.body.password === 'string' ? req.body.password : '';

  if (!nombre || !email || !password) {
    return res.status(400).json({ ok: false, message: 'nombre, email y password son obligatorios.' });
  }

  if (nombre.length > 100 || email.length > 100 || !/^\S+@\S+\.\S+$/.test(email)) {
    return res.status(400).json({ ok: false, message: 'El nombre o el email no tienen un formato válido.' });
  }

  if (password.length < 8 || password.length > 128) {
    return res.status(400).json({ ok: false, message: 'La contraseña debe tener entre 8 y 128 caracteres.' });
  }

  try {
    const passwordHash = await bcrypt.hash(password, 12);
    const { rows } = await pool.query(
      'INSERT INTO usuarios (nombre, email, password) VALUES ($1, $2, $3) RETURNING id, nombre, email',
      [nombre, email, passwordHash]
    );

    return res.status(201).json({
      ok: true,
      message: 'Usuario registrado correctamente.',
      usuario: rows[0]
    });
  } catch (error) {
    if (error.code === '23505') {
      return res.status(409).json({ ok: false, message: 'Ya existe un usuario con ese email.' });
    }

    console.error('Error al registrar usuario:', error.message);
    return res.status(500).json({ ok: false, message: 'No se pudo registrar el usuario.' });
  }
});

app.use((req, res) => {
  res.status(404).json({ ok: false, message: 'Ruta no encontrada.' });
});

app.use((error, req, res, next) => {
  if (error instanceof SyntaxError && error.status === 400 && 'body' in error) {
    return res.status(400).json({ ok: false, message: 'El cuerpo JSON no es válido.' });
  }

  console.error('Error inesperado:', error.message);
  return res.status(500).json({ ok: false, message: 'Error interno del servidor.' });
});

async function startServer() {
  if (!databaseUrl) {
    throw new Error('Falta la variable de entorno DATABASE_URL. Configúrala en Render o en tu entorno local.');
  }

  await pool.query(`
    CREATE TABLE IF NOT EXISTS usuarios (
      id SERIAL PRIMARY KEY,
      nombre VARCHAR(100) NOT NULL,
      email VARCHAR(100) UNIQUE NOT NULL,
      password VARCHAR(100) NOT NULL
    )
  `);

  return app.listen(port, () => {
    console.log(`UNI-PARK API escuchando en el puerto ${port}.`);
  });
}

if (require.main === module) {
  startServer().catch(async (error) => {
    console.error(error.message);
    await pool.end();
    process.exitCode = 1;
  });
}

module.exports = { app, pool, startServer };