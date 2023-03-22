	create database retail_store;
	use retail_store;
	
	create table rs_item (
		id int primary key auto_increment,
		name varchar(100),
		category int,
		type int,
		manufacturer varchar(100),
		barcode varchar(100),
		status int,
		price decimal(20,2),
		quantity int,
		unit varchar(20),
		version int,
		create_date timestamp, 
		created_by varchar(100), 
		update_date timestamp, 
		updated_by varchar(100),
		foreign key (category) references rs_category(id),
		foreign key (type) references rs_type (id),
		foreign key (status) references rs_status (id)
	);
	
	
	create table rs_type(
		id int primary key auto_increment,
		type varchar(100),
		value varchar(20)
	);
	
	create table rs_status(
		id int primary key auto_increment,
		type varchar(100),
		value varchar(20)
	);
	
	create table rs_category(
		id int primary key auto_increment,
		category varchar(100),
		parent_id int,
		foreign key (parent_id) references rs_category (id)
	);
	
	
	create table rs_user (
		id int primary key auto_increment, 
		name varchar(100), 
		address varchar(200), 
		email varchar(50), 
		mobile varchar(20), 
		status int;
		version int, 
		create_date timestamp, 
		created_by varchar(100), 
		update_date timestamp, 
		updated_by varchar(100),
		foreign key (status) references rs_status (id)
	);
	
	
	create table rs_order (
		id int primary key auto_increment,   
		order_number varchar(100), 
		status int, 
		user_id int,
		version int,
		create_date timestamp, 
		created_by varchar(100), 
		update_date timestamp, 
		updated_by varchar(100),
		foreign key (status) references rs_status(id),
		foreign key (user_id) references rs_user(id),
	);
	
	
	create table rs_order_detail (
		id int primary key auto_increment,  
		order_id int,
		item_id int, 
		quantity int, 
		foreign key (order_id) references rs_order (id),
		foreign key (item_id) references rs_item (id)	
	);
	
	
	create table rs_billing (
		id int primary key auto_increment, 
		order_id int, 
		trans_id varchar(100),
		status int,
		shipping_charges decimal(20,2),
		discount decimal(20,2),
		tax decimal(20,2),
		total_charged decimal(20,2),
		foreign key (order_id) references rs_order (id),
		foreign key (status) references rs_status(id)
	);
	
	
	 insert into rs_type(type, value) values('ITEM_TYPE', 'Packed');
	 insert into rs_type(type, value) values('ITEM_TYPE', 'Loose');
	 insert into rs_category(category, parent_id) values('Electronics', null);
	 insert into rs_category(category, parent_id) values('Grocery', null);
	 insert into rs_status(type, value) values('ITEM_STATUS', 'Available');
	 insert into rs_status(type, value) values('ITEM_STATUS', 'Out Of Stock');
	 insert into rs_status(type, value) values('ORDER_STATUS', 'Created');
	 insert into rs_status(type, value) values('ORDER_STATUS', 'Placed');
	 insert into rs_status(type, value) values('ORDER_STATUS', 'Shipped');
	 insert into rs_status(type, value) values('ORDER_STATUS', 'In Transit');
	 insert into rs_status(type, value) values('ORDER_STATUS', 'Delivered');
	 insert into rs_status(type, value) values('ORDER_STATUS', 'Cancelled');
	 insert into rs_status(type, value) values('USER_STATUS', 'Active');
	 insert into rs_status(type, value) values('USER_STATUS', 'Not Active');
	 insert into rs_status(type, value) values('BILLING_STATUS', 'Failed');
	 insert into rs_status(type, value) values('BILLING_STATUS', 'Paid');
	 
	 insert into rs_item(name, type, sub_type, manufacturer, barcode, status, price, quantity, unit, create_date, created_by, update_date, updated_by)
				 values('OnePlus Nord 2', 1,3, 'OnePlus', '0123456789', 1, 21000.50, 1, null, CURRENT_TIMESTAMP, 'Irfan Nagoo', CURRENT_TIMESTAMP, 'Irfan Nagoo');

	
	 insert into rs_user(name, address, email, mobile, status, version, create_date, created_by, update_date, updated_by)
				 values('Irfan Rashid Nagoo', 'Friends Colony, Kanli Bagh, Baramulla, J&K - 193101','irfan.nagoo@yahoo.com', '9149638033', 8, 1, CURRENT_TIMESTAMP, 'Irfan Nagoo', CURRENT_TIMESTAMP, 'Irfan Nagoo');
				 
	 insert into rs_order(order_number, status, version, user_id, create_date, created_by, update_date, updated_by)
				 values('OR12345', 3, 1, 1, CURRENT_TIMESTAMP, 'Irfan Nagoo', CURRENT_TIMESTAMP, 'Irfan Nagoo');
				 
	 insert into rs_order_detail(order_id, item_id, quantity, version, create_date, created_by, update_date, updated_by)
				 values(1, 1, 1, 1, CURRENT_TIMESTAMP, 'Irfan Nagoo', CURRENT_TIMESTAMP, 'Irfan Nagoo');
				 
	 insert into rs_order_detail(order_id, item_id, quantity, version, create_date, created_by, update_date, updated_by)
				 values(1, 2, 1, 1, CURRENT_TIMESTAMP, 'Irfan Nagoo', CURRENT_TIMESTAMP, 'Irfan Nagoo');