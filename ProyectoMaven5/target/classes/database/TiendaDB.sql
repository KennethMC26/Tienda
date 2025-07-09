drop database if exists TiendaProductosDB;
create database TiendaProductosDB;
use TiendaProductosDB;

-- Tabla de Usuarios
create table Usuarios (
    codigoUsuario int auto_increment,
    nombreUsuario varchar(50) not null,
    apellidoUsuario varchar(50) not null,
    correo varchar(100) not null unique,
    contraseña varchar(255) not null,
    direccion varchar(255),
    telefono varchar(20),
    fechaRegistro date not null,
    primary key pk_codigoUsuario (codigoUsuario)
);

-- Tabla de Productos
create table Productos (
    codigoProducto int auto_increment,
    nombreProducto varchar(100) not null,
    descripcion text,
    precio decimal(10,2) not null,
    stock int not null,
    categoria varchar(50),
    fechaIngreso date not null,
    primary key pk_codigoProducto (codigoProducto)
);

-- Tabla de Pedidos
create table Pedidos (
    codigoPedido int auto_increment,
    codigoUsuario int not null,
    fechaPedido date not null,
    estado enum('Pendiente', 'Procesando', 'Enviado', 'Entregado', 'Cancelado') default 'Pendiente',
    total decimal(10,2) not null,
    primary key pk_codigoPedido (codigoPedido),
    foreign key fk_pedido_usuario (codigoUsuario) references Usuarios(codigoUsuario)
);

-- Tabla DetallePedido (relación muchos a muchos entre Pedidos y Productos)
create table DetallePedido (
    codigoDetalle int auto_increment,
    codigoPedido int not null,
    codigoProducto int not null,
    cantidad int not null,
    precioUnitario decimal(10,2) not null,
    subtotal decimal(10,2) not null,
    primary key pk_codigoDetalle (codigoDetalle),
    foreign key fk_detalle_pedido (codigoPedido) references Pedidos(codigoPedido),
    foreign key fk_detalle_producto (codigoProducto) references Productos(codigoProducto)
);

-- Agregar Usuario
DELIMITER $$
create procedure sp_AgregarUsuario(
	in p_nombre varchar(50),
	in p_apellido varchar(50),
	in p_correo varchar(100),
	in p_contraseña varchar(255),
	in p_direccion varchar(255),
	in p_telefono varchar(20),
	in p_fechaRegistro date)
begin
	insert into Usuarios(nombreUsuario, apellidoUsuario, correo, contraseña, direccion, telefono, fechaRegistro)
	values (p_nombre, p_apellido, p_correo, p_contraseña, p_direccion, p_telefono, p_fechaRegistro);
end $$
DELIMITER ;

-- Listar Usuarios
DELIMITER $$
create procedure sp_ListarUsuarios()
begin
	select * from Usuarios;
end $$
DELIMITER ;

-- Buscar Usuario por ID
DELIMITER $$
create procedure sp_BuscarUsuario(in p_id int)
begin
	select * from Usuarios where codigoUsuario = p_id;
end $$
DELIMITER ;

-- Actualizar Usuario
DELIMITER $$
create procedure sp_ActualizarUsuario(
	in p_id int,
	in p_nombre varchar(50),
	in p_apellido varchar(50),
	in p_correo varchar(100),
	in p_contraseña varchar(255),
	in p_direccion varchar(255),
	in p_telefono varchar(20),
	in p_fechaRegistro date)
begin
	update Usuarios
	set nombreUsuario = p_nombre,
		apellidoUsuario = p_apellido,
		correo = p_correo,
		contraseña = p_contraseña,
		direccion = p_direccion,
		telefono = p_telefono,
		fechaRegistro = p_fechaRegistro
	where codigoUsuario = p_id;
end $$
DELIMITER ;

-- Eliminar Usuario
DELIMITER $$
create procedure sp_EliminarUsuario(in p_id int)
begin
	delete from Usuarios where codigoUsuario = p_id;
end $$
DELIMITER ;
-- Agregar Producto
DELIMITER $$
create procedure sp_AgregarProducto(
	in p_nombre varchar(100),
	in p_descripcion text,
	in p_precio decimal(10,2),
	in p_stock int,
	in p_categoria varchar(50),
	in p_fechaIngreso date)
begin
	insert into Productos(nombreProducto, descripcion, precio, stock, categoria, fechaIngreso)
	values (p_nombre, p_descripcion, p_precio, p_stock, p_categoria, p_fechaIngreso);
end $$
DELIMITER ;

-- Listar Productos
DELIMITER $$
create procedure sp_ListarProductos()
begin
	select * from Productos;
end $$
DELIMITER ;

-- Buscar Producto
DELIMITER $$
create procedure sp_BuscarProducto(in p_id int)
begin
	select * from Productos where codigoProducto = p_id;
end $$
DELIMITER ;

-- Actualizar Producto
DELIMITER $$
create procedure sp_ActualizarProducto(
	in p_id int,
	in p_nombre varchar(100),
	in p_descripcion text,
	in p_precio decimal(10,2),
	in p_stock int,
	in p_categoria varchar(50),
	in p_fechaIngreso date)
begin
	update Productos
	set nombreProducto = p_nombre,
		descripcion = p_descripcion,
		precio = p_precio,
		stock = p_stock,
		categoria = p_categoria,
		fechaIngreso = p_fechaIngreso
	where codigoProducto = p_id;
end $$
DELIMITER ;

-- Eliminar Producto
DELIMITER $$
create procedure sp_EliminarProducto(in p_id int)
begin
	delete from Productos where codigoProducto = p_id;
end $$
DELIMITER ;
-- Agregar Pedido
DELIMITER $$
create procedure sp_AgregarPedido(
	in p_codigoUsuario int,
	in p_fechaPedido date,
	in p_estado enum('Pendiente','Procesando','Enviado','Entregado','Cancelado'),
	in p_total decimal(10,2))
begin
	insert into Pedidos(codigoUsuario, fechaPedido, estado, total)
	values (p_codigoUsuario, p_fechaPedido, p_estado, p_total);
end $$
DELIMITER ;

-- Listar Pedidos
DELIMITER $$
create procedure sp_ListarPedidos()
begin
	select * from Pedidos;
end $$
DELIMITER ;

-- Buscar Pedido
DELIMITER $$
create procedure sp_BuscarPedido(in p_id int)
begin
	select * from Pedidos where codigoPedido = p_id;
end $$
DELIMITER ;

-- Actualizar Pedido
DELIMITER $$
create procedure sp_ActualizarPedido(
	in p_id int,
	in p_codigoUsuario int,
	in p_fechaPedido date,
	in p_estado enum('Pendiente','Procesando','Enviado','Entregado','Cancelado'),
	in p_total decimal(10,2))
begin
	update Pedidos
	set codigoUsuario = p_codigoUsuario,
		fechaPedido = p_fechaPedido,
		estado = p_estado,
		total = p_total
	where codigoPedido = p_id;
end $$
DELIMITER ;

-- Eliminar Pedido
DELIMITER $$
create procedure sp_EliminarPedido(in p_id int)
begin
	delete from Pedidos where codigoPedido = p_id;
end $$
DELIMITER ;
-- Agregar Detalle de Pedido
DELIMITER $$
create procedure sp_AgregarDetallePedido(
	in p_codigoPedido int,
	in p_codigoProducto int,
	in p_cantidad int,
	in p_precioUnitario decimal(10,2),
	in p_subtotal decimal(10,2))
begin
	insert into DetallePedido(codigoPedido, codigoProducto, cantidad, precioUnitario, subtotal)
	values (p_codigoPedido, p_codigoProducto, p_cantidad, p_precioUnitario, p_subtotal);
end $$
DELIMITER ;

-- Listar Detalles de Pedido
DELIMITER $$
create procedure sp_ListarDetallePedido()
begin
	select * from DetallePedido;
end $$
DELIMITER ;

-- Buscar Detalle por ID
DELIMITER $$
create procedure sp_BuscarDetallePedido(in p_id int)
begin
	select * from DetallePedido where codigoDetalle = p_id;
end $$
DELIMITER ;

-- Actualizar DetallePedido
DELIMITER $$
create procedure sp_ActualizarDetallePedido(
	in p_id int,
	in p_codigoPedido int,
	in p_codigoProducto int,
	in p_cantidad int,
	in p_precioUnitario decimal(10,2),
	in p_subtotal decimal(10,2))
begin
	update DetallePedido
	set codigoPedido = p_codigoPedido,
		codigoProducto = p_codigoProducto,
		cantidad = p_cantidad,
		precioUnitario = p_precioUnitario,
		subtotal = p_subtotal
	where codigoDetalle = p_id;
end $$
DELIMITER ;

-- Eliminar DetallePedido
DELIMITER $$
create procedure sp_EliminarDetallePedido(in p_id int)
begin
	delete from DetallePedido where codigoDetalle = p_id;
end $$
DELIMITER ;
CALL sp_AgregarUsuario(
    'Juan',
    'Pérez',
    'juan.perez@example.com',
    '12345678',         -- Asegúrate de encriptar contraseñas en sistemas reales
    'Calle Falsa 123',
    '555-1234',
    CURDATE()
);
CALL sp_AgregarProducto(
    'Laptop Gamer',
    'Laptop con procesador Intel i7 y 16GB RAM',
    1500.00,
    10,
    'Electrónica',
    CURDATE()
);
CALL sp_AgregarPedido(
    1,                -- códigoUsuario
    CURDATE(),        -- fechaPedido
    'Pendiente',      -- estado
    1500.00           -- total del pedido (puede ser suma del detalle)
);
CALL sp_AgregarDetallePedido(
    1,            -- códigoPedido
    1,            -- códigoProducto
    1,            -- cantidad
    1500.00,      -- precio unitario
    1500.00       -- subtotal (cantidad * precioUnitario)
);
