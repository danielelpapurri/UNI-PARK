-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-11-2023 a las 00:06:57
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Creación de la base de datos
--

CREATE DATABASE IF NOT EXISTS `cesmagparqueadero` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `cesmagparqueadero`;

--
-- Base de datos: `cesmagparqueadero`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `Id_empleado` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Apellido` varchar(50) NOT NULL,
  `NombreUsuario` varchar(50) NOT NULL,
  `Contraseña` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `espacios_estacionamiento`
--

CREATE TABLE `espacios_estacionamiento` (
  `Id_plaza` int(11) NOT NULL,
  `Nombre_plaza` varchar(50) NOT NULL,
  `Estado` enum('Ocupado','Libre') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `espacios_estacionamiento`
--

INSERT INTO `espacios_estacionamiento` (`Id_plaza`, `Nombre_plaza`, `Estado`) VALUES
(1, 'A-01', 'Libre'),
(2, 'A-02', 'Libre'),
(3, 'A-03', 'Libre'),
(4, 'A-04', 'Libre'),
(5, 'B-01', 'Libre'),
(6, 'B-02', 'Libre'),
(7, 'B-03', 'Libre'),
(8, 'B-04', 'Libre'),
(9, 'C-01', 'Libre'),
(10, 'C-02', 'Libre'),
(11, 'C-03', 'Libre'),
(12, 'C-04', 'Libre'),
(13, 'D-01', 'Libre'),
(14, 'D-02', 'Libre'),
(15, 'D-03', 'Libre'),
(16, 'D-04', 'Libre');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incidentes_problemas`
--

CREATE TABLE `incidentes_problemas` (
  `Id_incidente` int(11) NOT NULL,
  `Id_vehiculo` int(11) NOT NULL,
  `Descripcion` text NOT NULL,
  `Fecha_incidente` date NOT NULL,
  `Hora_incidente` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro_entrada`
--

CREATE TABLE `registro_entrada` (
  `Id_registro` int(11) NOT NULL,
  `Id_vehiculo` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Hora_entrada` time NOT NULL,
  `Ubicacion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro_salida`
--

CREATE TABLE `registro_salida` (
  `Id_registro` int(11) NOT NULL,
  `Id_vehiculo` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Hora_salida` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `Id_reserva` int(11) NOT NULL,
  `Id_plaza_estacionamiento` int(11) DEFAULT NULL,
  `Nombre_reservista` varchar(100) NOT NULL,
  `Fecha_reserva` date NOT NULL,
  `Hora_inicio_reserva` time NOT NULL,
  `Hora_fin_reserva` time NOT NULL,
  `Estado_reserva` enum('Activa','Caducada','Cancelada') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ticket`
--

CREATE TABLE `ticket` (
  `Cod_Factura` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Fecha_entrada` datetime NOT NULL,
  `Fecha_salida` datetime NOT NULL,
  `Importe` decimal(10,2) NOT NULL,
  `Id_incidente` int(11) DEFAULT NULL,
  `Id_vehiculo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `Id_vehiculo` int(11) NOT NULL,
  `Placa` varchar(15) NOT NULL,
  `Propietario` varchar(100) NOT NULL,
  `Condicion` varchar(255) DEFAULT NULL,
  `Tipo_vehiculo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`Id_empleado`);

--
-- Indices de la tabla `espacios_estacionamiento`
--
ALTER TABLE `espacios_estacionamiento`
  ADD PRIMARY KEY (`Id_plaza`);

--
-- Indices de la tabla `incidentes_problemas`
--
ALTER TABLE `incidentes_problemas`
  ADD PRIMARY KEY (`Id_incidente`),
  ADD KEY `Id_vehiculo` (`Id_vehiculo`);

--
-- Indices de la tabla `registro_entrada`
--
ALTER TABLE `registro_entrada`
  ADD PRIMARY KEY (`Id_registro`),
  ADD KEY `Id_vehiculo` (`Id_vehiculo`);

--
-- Indices de la tabla `registro_salida`
--
ALTER TABLE `registro_salida`
  ADD PRIMARY KEY (`Id_registro`),
  ADD KEY `Id_vehiculo` (`Id_vehiculo`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`Id_reserva`),
  ADD KEY `Id_plaza_estacionamiento` (`Id_plaza_estacionamiento`);

--
-- Indices de la tabla `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`Cod_Factura`),
  ADD KEY `Id_vehiculo` (`Id_vehiculo`),
  ADD KEY `Id_incidente` (`Id_incidente`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`Id_vehiculo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `Id_empleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `espacios_estacionamiento`
--
ALTER TABLE `espacios_estacionamiento`
  MODIFY `Id_plaza` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `incidentes_problemas`
--
ALTER TABLE `incidentes_problemas`
  MODIFY `Id_incidente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `registro_entrada`
--
ALTER TABLE `registro_entrada`
  MODIFY `Id_registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `registro_salida`
--
ALTER TABLE `registro_salida`
  MODIFY `Id_registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
  MODIFY `Id_reserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `ticket`
--
ALTER TABLE `ticket`
  MODIFY `Cod_Factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `Id_vehiculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `incidentes_problemas`
--
ALTER TABLE `incidentes_problemas`
  ADD CONSTRAINT `incidentes_problemas_ibfk_1` FOREIGN KEY (`Id_vehiculo`) REFERENCES `vehiculo` (`Id_vehiculo`);

--
-- Filtros para la tabla `registro_entrada`
--
ALTER TABLE `registro_entrada`
  ADD CONSTRAINT `registro_entrada_ibfk_1` FOREIGN KEY (`Id_vehiculo`) REFERENCES `vehiculo` (`Id_vehiculo`);

--
-- Filtros para la tabla `registro_salida`
--
ALTER TABLE `registro_salida`
  ADD CONSTRAINT `registro_salida_ibfk_1` FOREIGN KEY (`Id_vehiculo`) REFERENCES `vehiculo` (`Id_vehiculo`);

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`Id_plaza_estacionamiento`) REFERENCES `espacios_estacionamiento` (`Id_plaza`);

--
-- Filtros para la tabla `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`Id_vehiculo`) REFERENCES `vehiculo` (`Id_vehiculo`),
  ADD CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`Id_incidente`) REFERENCES `incidentes_problemas` (`Id_incidente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
