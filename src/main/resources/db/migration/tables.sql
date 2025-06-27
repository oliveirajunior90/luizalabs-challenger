CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY,
    nome VARCHAR(45)
);

CREATE TABLE pedidos (
     id_pedido INT,
     id_usuario INT REFERENCES usuarios(id_usuario),
     id_produto INT,
     valor_produto DECIMAL(10, 2),
     data_compra DATE,
     PRIMARY KEY (id_pedido, id_produto)
);