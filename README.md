# UNI-PARK API

Backend Node.js con Express y PostgreSQL, preparado para Render. El frontend estático se conserva en `public/`.

## Ejecutar localmente

Necesitas Node.js 20 o superior y una instancia PostgreSQL. Define `DATABASE_URL` con la cadena de conexión y ejecuta:

```bash
npm install
npm start
```

En PowerShell puedes configurar la variable para la sesión actual así:

```powershell
$env:DATABASE_URL = "postgresql://usuario:clave@localhost:5432/uni_park"
npm start
```

El servidor crea la tabla `usuarios` al iniciar. El esquema también está disponible en `schema.sql`.

## API

- `GET /` devuelve un mensaje de prueba.
- `POST /api/register` recibe JSON con `nombre`, `email` y `password`.

Ejemplo:

```json
{
	"nombre": "Ana Pérez",
	"email": "ana@example.com",
	"password": "una-clave-segura"
}
```

La contraseña se almacena como hash bcrypt, no como texto plano. Los errores se devuelven en JSON; un email duplicado responde con HTTP 409.

## Desplegar en Render

1. Sube el repositorio a GitHub.
2. En Render, crea un **Blueprint** y selecciona este repositorio.
3. Render leerá `render.yaml`, creará el servicio web y PostgreSQL, y conectará `DATABASE_URL` automáticamente.

El comando de inicio configurado es `npm start`. La variable `PORT` la proporciona Render.
