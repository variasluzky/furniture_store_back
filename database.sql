USE [master]
GO
/****** Object:  Database [furniture_store]    Script Date: 8/31/2024 11:42:37 PM ******/
CREATE DATABASE [furniture_store]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'furniture_store', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\furniture_store.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'furniture_store_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\furniture_store_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [furniture_store] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [furniture_store].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [furniture_store] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [furniture_store] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [furniture_store] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [furniture_store] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [furniture_store] SET ARITHABORT OFF 
GO
ALTER DATABASE [furniture_store] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [furniture_store] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [furniture_store] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [furniture_store] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [furniture_store] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [furniture_store] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [furniture_store] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [furniture_store] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [furniture_store] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [furniture_store] SET  DISABLE_BROKER 
GO
ALTER DATABASE [furniture_store] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [furniture_store] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [furniture_store] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [furniture_store] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [furniture_store] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [furniture_store] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [furniture_store] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [furniture_store] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [furniture_store] SET  MULTI_USER 
GO
ALTER DATABASE [furniture_store] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [furniture_store] SET DB_CHAINING OFF 
GO
ALTER DATABASE [furniture_store] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [furniture_store] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [furniture_store] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [furniture_store] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [furniture_store] SET QUERY_STORE = OFF
GO
USE [furniture_store]
GO
/****** Object:  User [springboot]    Script Date: 8/31/2024 11:42:37 PM ******/
CREATE USER [springboot] FOR LOGIN [springboot] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [springboot]
GO
ALTER ROLE [db_accessadmin] ADD MEMBER [springboot]
GO
ALTER ROLE [db_securityadmin] ADD MEMBER [springboot]
GO
ALTER ROLE [db_ddladmin] ADD MEMBER [springboot]
GO
ALTER ROLE [db_backupoperator] ADD MEMBER [springboot]
GO
ALTER ROLE [db_datareader] ADD MEMBER [springboot]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [springboot]
GO
ALTER ROLE [db_denydatareader] ADD MEMBER [springboot]
GO
ALTER ROLE [db_denydatawriter] ADD MEMBER [springboot]
GO
/****** Object:  Table [dbo].[catalog]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[catalog](
	[product_id] [bigint] IDENTITY(1,1) NOT NULL,
	[description] [varchar](250) NULL,
	[description_full] [varchar](max) NULL,
	[price] [numeric](38, 2) NOT NULL,
	[product_name] [varchar](100) NOT NULL,
	[status] [bit] NOT NULL,
	[stock] [int] NOT NULL,
	[color_code] [smallint] NOT NULL,
	[promotion_code] [smallint] NOT NULL,
	[type_of_item] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[product_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[color]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[color](
	[color_code] [smallint] IDENTITY(1,1) NOT NULL,
	[color_description] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[color_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[credit_card_detail]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[credit_card_detail](
	[payment_id] [bigint] IDENTITY(1,1) NOT NULL,
	[cardholder_first_name] [varchar](255) NOT NULL,
	[cardholder_last_name] [varchar](255) NULL,
	[credit_card_exp_date] [varbinary](255) NOT NULL,
	[cardholder_id] [varchar](255) NOT NULL,
	[credit_card_number] [varchar](255) NOT NULL,
	[cvv] [varchar](255) NOT NULL,
	[customer_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[payment_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_item]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_item](
	[order_item_id] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[product_id] [bigint] NOT NULL,
	[order_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[order_id] ASC,
	[order_item_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[order_id] [int] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[date] [date] NULL,
	[delivery] [bit] NULL,
	[total_price] [float] NULL,
	[customer_id] [int] NOT NULL,
	[status] [smallint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[payment](
	[receipt_num] [bigint] IDENTITY(1,1) NOT NULL,
	[payment_date] [date] NOT NULL,
	[payment_id] [bigint] NOT NULL,
	[customer_id] [int] NOT NULL,
	[order_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[receipt_num] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[permission_types]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[permission_types](
	[permission_id] [smallint] IDENTITY(1,1) NOT NULL,
	[permission_status] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[permission_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[promotion]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[promotion](
	[promotion_code] [smallint] IDENTITY(1,1) NOT NULL,
	[discount] [numeric](38, 2) NULL,
	[description] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[promotion_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[registered_customers]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[registered_customers](
	[customer_id] [int] IDENTITY(1,1) NOT NULL,
	[created_at] [date] NOT NULL,
	[email] [varchar](255) NOT NULL,
	[first_name] [varchar](255) NOT NULL,
	[last_name] [varchar](255) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[phone] [varchar](255) NOT NULL,
	[updated_at] [date] NOT NULL,
	[permission_type_id] [smallint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[customer_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UKbdjyi83mgsp0e97j6suhdoki3] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shopping_cart]    Script Date: 8/31/2024 11:42:37 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shopping_cart](
	[cart_id] [int] IDENTITY(1,1) NOT NULL,
	[created_at] [datetime2](6) NOT NULL,
	[updated_at] [datetime2](6) NOT NULL,
	[customer_id] [int] NOT NULL,
	[status_cart] [smallint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cart_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shopping_cart_item]    Script Date: 8/31/2024 11:42:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shopping_cart_item](
	[cart_item_id] [int] NOT NULL,
	[quantity_cart] [int] NOT NULL,
	[product_id] [bigint] NOT NULL,
	[cart_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cart_id] ASC,
	[cart_item_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shopping_cart_status]    Script Date: 8/31/2024 11:42:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shopping_cart_status](
	[status_cart] [smallint] IDENTITY(1,1) NOT NULL,
	[status_description] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[status_cart] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[status]    Script Date: 8/31/2024 11:42:38 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[status](
	[status_id] [smallint] IDENTITY(1,1) NOT NULL,
	[description_status] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[status_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[catalog]  WITH CHECK ADD  CONSTRAINT [FKm3flwtyr3jqsbo7sj4h211u9m] FOREIGN KEY([color_code])
REFERENCES [dbo].[color] ([color_code])
GO
ALTER TABLE [dbo].[catalog] CHECK CONSTRAINT [FKm3flwtyr3jqsbo7sj4h211u9m]
GO
ALTER TABLE [dbo].[catalog]  WITH CHECK ADD  CONSTRAINT [FKtk9o934k0ubfllirqu27spequ] FOREIGN KEY([promotion_code])
REFERENCES [dbo].[promotion] ([promotion_code])
GO
ALTER TABLE [dbo].[catalog] CHECK CONSTRAINT [FKtk9o934k0ubfllirqu27spequ]
GO
ALTER TABLE [dbo].[credit_card_detail]  WITH CHECK ADD  CONSTRAINT [FKqchlf4tmhwvdugekcgaaxl5b6] FOREIGN KEY([customer_id])
REFERENCES [dbo].[registered_customers] ([customer_id])
GO
ALTER TABLE [dbo].[credit_card_detail] CHECK CONSTRAINT [FKqchlf4tmhwvdugekcgaaxl5b6]
GO
ALTER TABLE [dbo].[order_item]  WITH CHECK ADD  CONSTRAINT [FK4gwxklf05yiyjh3tvesalywgk] FOREIGN KEY([product_id])
REFERENCES [dbo].[catalog] ([product_id])
GO
ALTER TABLE [dbo].[order_item] CHECK CONSTRAINT [FK4gwxklf05yiyjh3tvesalywgk]
GO
ALTER TABLE [dbo].[order_item]  WITH CHECK ADD  CONSTRAINT [FKt4dc2r9nbvbujrljv3e23iibt] FOREIGN KEY([order_id])
REFERENCES [dbo].[orders] ([order_id])
GO
ALTER TABLE [dbo].[order_item] CHECK CONSTRAINT [FKt4dc2r9nbvbujrljv3e23iibt]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FK4adkd09gxmrp46ohi2oqc0q2h] FOREIGN KEY([status])
REFERENCES [dbo].[status] ([status_id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FK4adkd09gxmrp46ohi2oqc0q2h]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FKtpgjp68rqtsdw37jeba57g5cq] FOREIGN KEY([customer_id])
REFERENCES [dbo].[registered_customers] ([customer_id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FKtpgjp68rqtsdw37jeba57g5cq]
GO
ALTER TABLE [dbo].[payment]  WITH CHECK ADD  CONSTRAINT [FKlouu98csyullos9k25tbpk4va] FOREIGN KEY([order_id])
REFERENCES [dbo].[orders] ([order_id])
GO
ALTER TABLE [dbo].[payment] CHECK CONSTRAINT [FKlouu98csyullos9k25tbpk4va]
GO
ALTER TABLE [dbo].[payment]  WITH CHECK ADD  CONSTRAINT [FKnr5s4nnr6pm1tu8r3qx25bd0n] FOREIGN KEY([payment_id])
REFERENCES [dbo].[credit_card_detail] ([payment_id])
GO
ALTER TABLE [dbo].[payment] CHECK CONSTRAINT [FKnr5s4nnr6pm1tu8r3qx25bd0n]
GO
ALTER TABLE [dbo].[payment]  WITH CHECK ADD  CONSTRAINT [FKp787oiuga7kqbofgc9susxcy7] FOREIGN KEY([customer_id])
REFERENCES [dbo].[registered_customers] ([customer_id])
GO
ALTER TABLE [dbo].[payment] CHECK CONSTRAINT [FKp787oiuga7kqbofgc9susxcy7]
GO
ALTER TABLE [dbo].[registered_customers]  WITH CHECK ADD  CONSTRAINT [FKgdivk9lixmrxxo0n4bdgclexb] FOREIGN KEY([permission_type_id])
REFERENCES [dbo].[permission_types] ([permission_id])
GO
ALTER TABLE [dbo].[registered_customers] CHECK CONSTRAINT [FKgdivk9lixmrxxo0n4bdgclexb]
GO
ALTER TABLE [dbo].[shopping_cart]  WITH CHECK ADD  CONSTRAINT [FK7eepkcafxxa1osxxvnrc36mrl] FOREIGN KEY([customer_id])
REFERENCES [dbo].[registered_customers] ([customer_id])
GO
ALTER TABLE [dbo].[shopping_cart] CHECK CONSTRAINT [FK7eepkcafxxa1osxxvnrc36mrl]
GO
ALTER TABLE [dbo].[shopping_cart]  WITH CHECK ADD  CONSTRAINT [FKs9265qnupyodw6j4dcg6uju7b] FOREIGN KEY([status_cart])
REFERENCES [dbo].[shopping_cart_status] ([status_cart])
GO
ALTER TABLE [dbo].[shopping_cart] CHECK CONSTRAINT [FKs9265qnupyodw6j4dcg6uju7b]
GO
ALTER TABLE [dbo].[shopping_cart_item]  WITH CHECK ADD  CONSTRAINT [FK48fql3nsok87el4ip3lyb8wox] FOREIGN KEY([product_id])
REFERENCES [dbo].[catalog] ([product_id])
GO
ALTER TABLE [dbo].[shopping_cart_item] CHECK CONSTRAINT [FK48fql3nsok87el4ip3lyb8wox]
GO
ALTER TABLE [dbo].[shopping_cart_item]  WITH CHECK ADD  CONSTRAINT [FKaca5wd2ofy5e0dipn1tiay9ru] FOREIGN KEY([cart_id])
REFERENCES [dbo].[shopping_cart] ([cart_id])
GO
ALTER TABLE [dbo].[shopping_cart_item] CHECK CONSTRAINT [FKaca5wd2ofy5e0dipn1tiay9ru]
GO
ALTER TABLE [dbo].[catalog]  WITH CHECK ADD CHECK  (([stock]>=(0)))
GO
ALTER TABLE [dbo].[catalog]  WITH CHECK ADD CHECK  (([type_of_item]='CHAIR' OR [type_of_item]='TABLE'))
GO
USE [master]
GO
ALTER DATABASE [furniture_store] SET  READ_WRITE 
GO
