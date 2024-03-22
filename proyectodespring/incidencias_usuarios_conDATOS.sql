-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 09-01-2023 a las 11:52:05
-- Versión del servidor: 8.0.28
-- Versión de PHP: 8.0.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `incidencias`
--

DROP DATABASE IF EXISTS incidencias;
CREATE DATABASE incidencias;
USE incidencias;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` char(9) COLLATE utf8mb4_general_ci NOT NULL,
  `nombre` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `direccion` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `telefono` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_usuario` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `nombre`, `direccion`, `telefono`, `id_usuario`) VALUES
('1234', 'oliver', 'calle', '922333344', 9),
('12345678', 'ruben', 'calle', '696969696', 1),
('5252', 'lucas', 'calle', '987654', 14),
('7777', 'pablo', 'calle', '987654', 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipos_trabajo`
--

CREATE TABLE `equipos_trabajo` (
  `id_ticket` int NOT NULL,
  `dni` char(9) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `equipos_trabajo`
--

INSERT INTO `equipos_trabajo` (`id_ticket`, `dni`) VALUES
(1, '4321'),
(3, '4321'),
(4, '4321'),
(5, '4321'),
(8, '4321'),
(1, '87654321'),
(3, '87654321'),
(7, '87654321');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gestores`
--

CREATE TABLE `gestores` (
  `dni` char(9) COLLATE utf8mb4_general_ci NOT NULL,
  `nombre` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `apellidos` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_usuario` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `gestores`
--

INSERT INTO `gestores` (`dni`, `nombre`, `apellidos`, `id_usuario`) VALUES
('4321', 'sara', 'hdez', 10),
('87654321', 'alberto', 'hdez', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN'),
(3, 'usuario'),
(4, 'admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seguimiento_tickets`
--

CREATE TABLE `seguimiento_tickets` (
  `id_seguimiento` int NOT NULL,
  `fecha` timestamp NOT NULL,
  `comentario` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `id_ticket` int NOT NULL,
  `dni` char(9) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `seguimiento_tickets`
--

INSERT INTO `seguimiento_tickets` (`id_seguimiento`, `fecha`, `comentario`, `id_ticket`, `dni`) VALUES
(1, '2022-12-19 22:37:24', 'se abre expediente de incidencia', 1, '87654321'),
(2, '2022-12-19 22:37:24', 'se inicia la busqueda de error', 1, '87654321'),
(4, '2022-12-27 20:25:01', 'se inicia incidencia', 3, '87654321'),
(7, '2022-12-19 22:37:24', 'se inicia la busqueda de error', 1, '4321'),
(8, '2022-12-27 20:25:01', 'otro', 3, '87654321'),
(9, '2022-12-27 20:25:01', 'otro mas', 3, '4321');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tickets`
--

CREATE TABLE `tickets` (
  `id_ticket` int NOT NULL,
  `fecha_inicio` timestamp NOT NULL,
  `fecha_fin` timestamp NULL DEFAULT NULL,
  `descripcion` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `estado` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `id_cliente` char(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `tickets`
--

INSERT INTO `tickets` (`id_ticket`, `fecha_inicio`, `fecha_fin`, `descripcion`, `estado`, `id_cliente`) VALUES
(1, '2022-12-19 22:36:42', NULL, 'esta es una prueba de save', 'Abierto', '12345678'),
(3, '2022-12-19 22:36:42', NULL, 'probando', 'Abierto', '1234'),
(4, '2022-12-19 22:36:42', NULL, 'probando a ver si funcionan los gestores aleatorios', 'Abierto', '1234'),
(5, '2022-12-19 22:36:42', NULL, 'otra prueba de aleatorio', 'Abierto', '1234'),
(7, '2022-12-19 22:36:42', NULL, 'proban2', 'Abierto', '1234'),
(8, '2022-12-19 22:36:42', NULL, 'esta es una prueba de save desde v2', 'Abierto', '12345678');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(250) NOT NULL,
  `id_rol` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `username`, `password`, `id_rol`) VALUES
(1, 'ruben', '$2a$12$VzG57UQKCAFQIUNID9Sx4.1t7Gs2mj74NUXie4XsKwPYyrsV8T.vO', 1),
(2, 'alberto', '$2a$12$LGZoV0PzTresjCWJdL2laebDAwEhV.pIwdUkChzTnc1g4aU5CO70C', 2),
(9, 'oliver', '$2a$10$BkkzG5xVJQNcwByTxGvjE.8p/4/afcYoIWWgYiO1rp2W79DdrTMvW', 1),
(10, 'sara', '$2a$10$J6DxVGsHnaYRrMWJC1KmQOP1un7a8PkezQH9cH4miM1JjCoHTSiiK', 2),
(14, 'lucas', '$2a$10$0NbuhQLCsuYVxoN/nhyAeOGXVO4GiFGDIN3HbHtepi8qEuU8DKhxW', 1),
(15, 'pablo', '$2a$10$JJHgZTD522vDG5.b7RYmjuND5LBBoTi9vYVU5Nu2oZqZICTCOtEoK', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `fk_usuarioClientes` (`id_usuario`);

--
-- Indices de la tabla `equipos_trabajo`
--
ALTER TABLE `equipos_trabajo`
  ADD PRIMARY KEY (`id_ticket`,`dni`),
  ADD KEY `fk_gestores2` (`dni`);

--
-- Indices de la tabla `gestores`
--
ALTER TABLE `gestores`
  ADD PRIMARY KEY (`dni`),
  ADD KEY `fk_usuario` (`id_usuario`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `seguimiento_tickets`
--
ALTER TABLE `seguimiento_tickets`
  ADD PRIMARY KEY (`id_seguimiento`),
  ADD KEY `fk_tickets` (`id_ticket`),
  ADD KEY `fk_gestores` (`dni`);

--
-- Indices de la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id_ticket`),
  ADD KEY `fk_clientes` (`id_cliente`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `fk_rol` (`id_rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `seguimiento_tickets`
--
ALTER TABLE `seguimiento_tickets`
  MODIFY `id_seguimiento` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `tickets`
--
ALTER TABLE `tickets`
  MODIFY `id_ticket` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `fk_usuarioClientes` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `equipos_trabajo`
--
ALTER TABLE `equipos_trabajo`
  ADD CONSTRAINT `fk_gestores2` FOREIGN KEY (`dni`) REFERENCES `gestores` (`dni`),
  ADD CONSTRAINT `fk_ticket` FOREIGN KEY (`id_ticket`) REFERENCES `tickets` (`id_ticket`);

--
-- Filtros para la tabla `gestores`
--
ALTER TABLE `gestores`
  ADD CONSTRAINT `fk_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `seguimiento_tickets`
--
ALTER TABLE `seguimiento_tickets`
  ADD CONSTRAINT `fk_gestores` FOREIGN KEY (`dni`) REFERENCES `gestores` (`dni`),
  ADD CONSTRAINT `fk_tickets` FOREIGN KEY (`id_ticket`) REFERENCES `tickets` (`id_ticket`);

--
-- Filtros para la tabla `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `fk_clientes` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_rol` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
