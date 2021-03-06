USE [master]
GO
/****** Object:  Database [UMM]    Script Date: 31/03/2022 10:53:05 AM ******/
CREATE DATABASE [UMM]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'UMM', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\UMM.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'UMM_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\UMM_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [UMM] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [UMM].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [UMM] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [UMM] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [UMM] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [UMM] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [UMM] SET ARITHABORT OFF 
GO
ALTER DATABASE [UMM] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [UMM] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [UMM] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [UMM] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [UMM] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [UMM] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [UMM] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [UMM] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [UMM] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [UMM] SET  DISABLE_BROKER 
GO
ALTER DATABASE [UMM] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [UMM] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [UMM] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [UMM] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [UMM] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [UMM] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [UMM] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [UMM] SET RECOVERY FULL 
GO
ALTER DATABASE [UMM] SET  MULTI_USER 
GO
ALTER DATABASE [UMM] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [UMM] SET DB_CHAINING OFF 
GO
ALTER DATABASE [UMM] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [UMM] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [UMM] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'UMM', N'ON'
GO
USE [UMM]
GO
/****** Object:  Table [dbo].[UserData]    Script Date: 31/03/2022 10:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UserData](
	[UserId] [int] IDENTITY(1,1) NOT NULL,
	[UserName] [varchar](50) NOT NULL,
	[FName] [varchar](100) NULL,
	[LName] [varchar](100) NULL,
	[Permissions] [int] NULL,
	[Pw] [varchar](100) NULL,
	[Activated] [bit] NOT NULL,
	[Email] [varchar](100) NULL,
	[PhoneNo] [varchar](30) NULL,
	[Gender] [varchar](10) NULL,
	[DOB] [varchar](30) NULL,
	[Nationality] [varchar](50) NULL,
	[PasswordEDate] [datetime] NULL,
	[SetupDate] [datetime] NULL,
	[Retired] [bit] NOT NULL,
	[Logged_in] [bit] NULL,
 CONSTRAINT [PK_UserInfoD] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 80) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[ActivateDeactivateUser]    Script Date: 31/03/2022 10:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[ActivateDeactivateUser]
	-- Add the parameters for the stored procedure here
	@Username varchar(50), @activated bit, @userid int
	
AS
declare @errorcode int, @errormessage varchar(100), @ID int
-- Grab error value
DECLARE @ERR INT = 0;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

BEGIN TRY
IF EXISTS (SELECT * FROM [dbo].[UserData] WHERE UserId = @userid )
BEGIN
			UPDATE [dbo].[UserData]
			SET [Activated] = @activated,
			retired = @activated    
			WHERE userId = @userid
       
		SET @ERR = @@ERROR;
		IF (@ERR = 0)
		BEGIN
			SET @errorcode = 1
			SET @errormessage = 'User Activated Successfully!'
		END
		ELSE
		BEGIN
			SET @errorcode = 0
			SET @errormessage = 'Record not Updated'
		END

END
 
ELSE
BEGIN  
		SET @errorcode = 0
		SET @errormessage = 'User does not exist'
END
	  
	  select @errorcode as Errorcode, @errormessage as Errormessage
END TRY
BEGIN CATCH
		select 0 as Errorcode, ERROR_MESSAGE() as Errormessage
END CATCH



/****** Object:  StoredProcedure [dbo].[AlterUser]    Script Date: 01/04/2022 12:23:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[AlterUser]
	-- Add the parameters for the stored procedure here
	@Username varchar(50), @password varchar(100), @fname varchar(100),
	 @lname varchar(100), @email varchar(100), @phoneno varchar(30), 
	 @gender varchar(10), @dob varchar(30), @nationality varchar(50),
	@permission int, @passwordenddate datetime,
	 @activated bit, @userid int
	
AS
declare @errorcode int, @errormessage varchar(100), @ID int
-- Grab error value
DECLARE @ERR INT = 0;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

BEGIN TRY
IF EXISTS (SELECT * FROM [dbo].[UserData] WHERE UserName = @Username )
BEGIN
			UPDATE [dbo].[UserData]
			SET [UserName] = @Username
			,[FName] = @fname
			,[LName] = @lname
			,[Permissions] = @permission
			,[Pw] = @password
			,[Activated] = @activated
			,[Email] = @email
			,[PhoneNo] = @phoneno
			,[Gender] = @gender
			,[DOB] = @dob
			,[Nationality] = @nationality
			,[PasswordEDate] = @passwordenddate
      
			WHERE UserName = @Username
       
		SET @ERR = @@ERROR;
		IF (@ERR = 0)
		BEGIN
			SET @errorcode = 1
			SET @errormessage = 'User Updated Successfully!'
		END
		ELSE
		BEGIN
			SET @errorcode = 0
			SET @errormessage = 'Record not Updated'
		END

END
 
ELSE
BEGIN  
		SET @errorcode = 0
		SET @errormessage = 'User does not exist'
END
	  
	  select @errorcode as Errorcode, @errormessage as Errormessage
END TRY
BEGIN CATCH
		select 0 as Errorcode, ERROR_MESSAGE() as Errormessage
END CATCH




GO
/****** Object:  StoredProcedure [dbo].[createusers]    Script Date: 31/03/2022 10:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
create PROCEDURE [dbo].[createusers]
	-- Add the parameters for the stored procedure here
	@Username varchar(50), @password varchar(100), @fname varchar(100),
	 @lname varchar(100), @email varchar(100), @phoneno varchar(30), 
	 @gender varchar(10), @dob varchar(30), @nationality varchar(50),
	@permission int, @passwordenddate datetime,
	 @activated bit
	
AS
declare @errorcode int, @errormessage varchar(100), @ID int
-- Grab error value
DECLARE @ERR INT = 0;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

BEGIN TRY
IF NOT EXISTS (SELECT * FROM [dbo].[UserData] WHERE UserName = @Username )
BEGIN
        INSERT INTO [dbo].[UserData]
		 ([UserName]
           ,[FName]
           ,[LName]
           ,[Permissions]
           ,[Pw]
           ,[Activated]
           ,[Email]
           ,[PhoneNo]
           ,[Gender]
           ,[DOB]
           ,[Nationality]
           ,[PasswordEDate]
           ,[SetupDate]
           ,[Retired]
           ,[Logged_in])
         VALUES( @Username,@fname,@lname,@permission, @password, 
				@activated, @email,@phoneno,@gender,@dob,@nationality
				,@passwordenddate, GETDATE(),0,0)
       
		SET @ERR = @@ERROR;
		IF (@ERR = 0)
		BEGIN
			SET @errorcode = 1
			SET @errormessage = 'User created Successfully!'
		END
		ELSE
		BEGIN
			SET @errorcode = 0
			SET @errormessage = 'Record not inserted'
		END

END
 
ELSE
BEGIN  
		SET @errorcode = 0
		SET @errormessage = 'UserName already exist'
END
	  
	  select @errorcode as Errorcode, @errormessage as Errormessage
END TRY
BEGIN CATCH
		select 0 as Errorcode, ERROR_MESSAGE() as Errormessage
END CATCH




GO
/****** Object:  StoredProcedure [dbo].[ViewUser]    Script Date: 31/03/2022 10:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
create PROCEDURE [dbo].[ViewUser]
	
AS
declare @errorcode int, @errormessage varchar(100), @ID int
-- Grab error value
DECLARE @ERR INT = 0;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

BEGIN TRY
	SELECT TOP(100) * FROM [dbo].[UserData] 
END TRY
BEGIN CATCH
		select 0 as Errorcode, ERROR_MESSAGE() as Errormessage
END CATCH


/****** Object:  StoredProcedure [dbo].[FindUser]    Script Date: 01/04/2022 12:26:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[FindUser] @username VARCHAR(50)
	
AS
declare @errorcode int, @errormessage varchar(100), @ID int
-- Grab error value
DECLARE @ERR INT = 0;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

BEGIN TRY
	SELECT TOP(1) * FROM [dbo].[UserData]  WHERE USERNAME = @username
END TRY
BEGIN CATCH
		select 0 as Errorcode, ERROR_MESSAGE() as Errormessage
END CATCH




GO
USE [master]
GO
ALTER DATABASE [UMM] SET  READ_WRITE 
GO
