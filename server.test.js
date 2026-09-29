const test = require('node:test');
const assert = require('node:assert/strict');
const bcrypt = require('bcryptjs');
const { app, pool } = require('./server');

test('health and user registration endpoints', async (t) => {
  const originalQuery = pool.query;
  let savedUser;

  pool.query = async (sql, values) => {
    assert.match(sql, /INSERT INTO usuarios/);

    if (savedUser && savedUser.email === values[1]) {
      const error = new Error('duplicate email');
      error.code = '23505';
      throw error;
    }

    savedUser = {
      id: 1,
      nombre: values[0],
      email: values[1],
      password: values[2]
    };

    return { rows: [{ id: savedUser.id, nombre: savedUser.nombre, email: savedUser.email }] };
  };

  const server = app.listen(0);
  await new Promise((resolve) => server.once('listening', resolve));
  t.after(async () => {
    await new Promise((resolve, reject) => {
      server.close((error) => error ? reject(error) : resolve());
    });
    pool.query = originalQuery;
    await pool.end();
  });

  const baseUrl = `http://127.0.0.1:${server.address().port}`;
  const healthResponse = await fetch(baseUrl);
  assert.equal(healthResponse.status, 200);
  assert.equal((await healthResponse.json()).ok, true);

  const register = (payload) => fetch(`${baseUrl}/api/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });

  const createdResponse = await register({
    nombre: ' Ana ',
    email: 'ANA@example.com',
    password: 'clave-segura-123'
  });
  assert.equal(createdResponse.status, 201);
  assert.equal((await createdResponse.json()).usuario.email, 'ana@example.com');
  assert.equal(await bcrypt.compare('clave-segura-123', savedUser.password), true);

  const duplicateResponse = await register({
    nombre: 'Ana',
    email: 'ana@example.com',
    password: 'clave-segura-123'
  });
  assert.equal(duplicateResponse.status, 409);

  const invalidResponse = await register({ nombre: '', email: '', password: '' });
  assert.equal(invalidResponse.status, 400);
});