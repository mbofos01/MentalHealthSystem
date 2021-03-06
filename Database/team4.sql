USE [master]
GO
/****** Object:  Database [team4]    Script Date: 9/5/2022 19:51:28 ******/
CREATE DATABASE [team4]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'team4_Data', FILENAME = N'H:\SQLData\team4_Data.mdf' , SIZE = 8192KB , MAXSIZE = 10240KB , FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'team4_Log', FILENAME = N'F:\SQLLogs\team4_Log.ldf' , SIZE = 2048KB , MAXSIZE = 3072KB , FILEGROWTH = 1024KB )
GO
ALTER DATABASE [team4] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [team4].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [team4] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [team4] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [team4] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [team4] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [team4] SET ARITHABORT OFF 
GO
ALTER DATABASE [team4] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [team4] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [team4] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [team4] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [team4] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [team4] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [team4] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [team4] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [team4] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [team4] SET  ENABLE_BROKER 
GO
ALTER DATABASE [team4] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [team4] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [team4] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [team4] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [team4] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [team4] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [team4] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [team4] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [team4] SET  MULTI_USER 
GO
ALTER DATABASE [team4] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [team4] SET DB_CHAINING OFF 
GO
ALTER DATABASE [team4] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [team4] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [team4] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [team4] SET QUERY_STORE = OFF
GO
USE [team4]
GO
/****** Object:  User [team4]    Script Date: 9/5/2022 19:51:29 ******/
CREATE USER [team4] FOR LOGIN [team4] WITH DEFAULT_SCHEMA=[team4]
GO
ALTER ROLE [db_owner] ADD MEMBER [team4]
GO
/****** Object:  Schema [team4]    Script Date: 9/5/2022 19:51:29 ******/
CREATE SCHEMA [team4]
GO
/****** Object:  Table [team4].[Allergy]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Allergy](
	[allergy_id] [int] IDENTITY(0,1) NOT NULL,
	[patient_id] [int] NOT NULL,
	[drug_id] [int] NOT NULL,
	[accepted] [bit] NOT NULL,
	[date] [date] NOT NULL,
	[last_updated] [date] NULL,
 CONSTRAINT [Allergy_PK] PRIMARY KEY CLUSTERED 
(
	[allergy_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Appointment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Appointment](
	[appoint_id] [int] IDENTITY(0,1) NOT NULL,
	[doctor_id] [int] NOT NULL,
	[patient_id] [int] NOT NULL,
	[clinic_id] [int] NOT NULL,
	[date] [date] NOT NULL,
	[time] [nvarchar](100) NOT NULL,
	[dropIn] [bit] NOT NULL,
	[receptionist_id] [int] NOT NULL,
	[attended] [bit] NOT NULL,
 CONSTRAINT [Appointment_PK] PRIMARY KEY CLUSTERED 
(
	[appoint_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[AppointmentRecord]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[AppointmentRecord](
	[record_id] [int] NOT NULL,
	[appoint_id] [int] NOT NULL,
 CONSTRAINT [AppointmentRecord_PK] PRIMARY KEY CLUSTERED 
(
	[appoint_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Clinic]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Clinic](
	[clinic_id] [int] IDENTITY(0,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[telephone] [nvarchar](20) NULL,
 CONSTRAINT [Clinics_PK] PRIMARY KEY CLUSTERED 
(
	[clinic_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Comments]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Comments](
	[patient_id] [int] NOT NULL,
	[doctor_id] [int] NOT NULL,
	[comment_id] [int] IDENTITY(1,1) NOT NULL,
	[comment] [text] NOT NULL,
	[date] [date] NOT NULL,
 CONSTRAINT [PK_Comments] PRIMARY KEY CLUSTERED 
(
	[comment_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [team4].[Condition]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Condition](
	[condition_id] [int] IDENTITY(0,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
 CONSTRAINT [Condition_PK] PRIMARY KEY CLUSTERED 
(
	[condition_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Doctor]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Doctor](
	[doctor_id] [int] IDENTITY(0,1) NOT NULL,
	[username] [nvarchar](100) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[clinic_id] [int] NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[surname] [varchar](100) NOT NULL,
 CONSTRAINT [Doctor_PK] PRIMARY KEY CLUSTERED 
(
	[doctor_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Drug]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Drug](
	[drug_id] [int] IDENTITY(0,1) NOT NULL,
	[commercial_name] [nvarchar](100) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[side_effect] [nvarchar](100) NULL,
 CONSTRAINT [Drug_PK] PRIMARY KEY CLUSTERED 
(
	[drug_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[HealthService]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[HealthService](
	[hs_id] [int] IDENTITY(0,1) NOT NULL,
	[username] [nvarchar](100) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[surname] [nvarchar](100) NOT NULL,
	[email] [nvarchar](100) NOT NULL,
 CONSTRAINT [HealthService_PK] PRIMARY KEY CLUSTERED 
(
	[hs_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[MedicalRecordsStaff]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[MedicalRecordsStaff](
	[records_id] [numeric](38, 0) IDENTITY(0,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[surname] [nvarchar](100) NOT NULL,
	[email] [nvarchar](100) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[username] [nvarchar](100) NULL,
 CONSTRAINT [MedicalRecordsStaff_PK] PRIMARY KEY CLUSTERED 
(
	[records_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Patient]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Patient](
	[patient_id] [int] IDENTITY(0,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[surname] [nvarchar](100) NOT NULL,
	[birthday] [date] NOT NULL,
	[email] [nvarchar](100) NULL,
	[telephone] [nvarchar](100) NULL,
	[alive] [bit] NOT NULL,
 CONSTRAINT [Patient_PK] PRIMARY KEY CLUSTERED 
(
	[patient_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[PatientDoctor]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[PatientDoctor](
	[doctor_id] [int] NOT NULL,
	[patient_id] [int] NOT NULL,
 CONSTRAINT [PatientDoctor_PK] PRIMARY KEY CLUSTERED 
(
	[doctor_id] ASC,
	[patient_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[PendingDeaths]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[PendingDeaths](
	[death_id] [int] IDENTITY(0,1) NOT NULL,
	[doctor_id] [int] NOT NULL,
	[patient_id] [int] NOT NULL,
	[date] [date] NOT NULL,
 CONSTRAINT [PendingDeaths_PK] PRIMARY KEY CLUSTERED 
(
	[death_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Receptionist]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Receptionist](
	[id] [int] IDENTITY(0,1) NOT NULL,
	[username] [nvarchar](100) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[email] [nvarchar](100) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[surname] [nvarchar](100) NOT NULL,
	[clinic_id] [int] NOT NULL,
 CONSTRAINT [Receptionist_PK] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Record]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Record](
	[record_id] [int] IDENTITY(0,1) NOT NULL,
	[doctor_id] [int] NOT NULL,
	[date] [date] NOT NULL,
	[selfharm] [bit] NOT NULL,
	[threat] [bit] NOT NULL,
	[overdose] [bit] NOT NULL,
	[condition_id] [int] NOT NULL,
	[last_update] [date] NULL,
	[treatment_id] [int] NULL,
	[patient_id] [int] NOT NULL,
	[accepted] [bit] NOT NULL,
	[underdose] [bit] NOT NULL,
 CONSTRAINT [Record_PK] PRIMARY KEY CLUSTERED 
(
	[record_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [team4].[Treatment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [team4].[Treatment](
	[treatment_id] [int] IDENTITY(0,1) NOT NULL,
	[patient_id] [int] NOT NULL,
	[doctor_id] [int] NOT NULL,
	[dose] [int] NULL,
	[comments] [text] NULL,
	[warning] [bit] NOT NULL,
	[drug_id] [int] NOT NULL,
	[accepted] [bit] NOT NULL,
	[date] [date] NOT NULL,
	[last_update] [date] NULL,
 CONSTRAINT [Treatment_PK] PRIMARY KEY CLUSTERED 
(
	[treatment_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [team4].[Allergy]  WITH CHECK ADD  CONSTRAINT [Allergy_FK] FOREIGN KEY([drug_id])
REFERENCES [team4].[Drug] ([drug_id])
GO
ALTER TABLE [team4].[Allergy] CHECK CONSTRAINT [Allergy_FK]
GO
ALTER TABLE [team4].[Allergy]  WITH CHECK ADD  CONSTRAINT [Allergy_FK_1] FOREIGN KEY([patient_id])
REFERENCES [team4].[Patient] ([patient_id])
GO
ALTER TABLE [team4].[Allergy] CHECK CONSTRAINT [Allergy_FK_1]
GO
ALTER TABLE [team4].[Appointment]  WITH CHECK ADD  CONSTRAINT [Appointment_FK] FOREIGN KEY([patient_id])
REFERENCES [team4].[Patient] ([patient_id])
GO
ALTER TABLE [team4].[Appointment] CHECK CONSTRAINT [Appointment_FK]
GO
ALTER TABLE [team4].[Appointment]  WITH CHECK ADD  CONSTRAINT [Appointment_FK_1] FOREIGN KEY([clinic_id])
REFERENCES [team4].[Clinic] ([clinic_id])
GO
ALTER TABLE [team4].[Appointment] CHECK CONSTRAINT [Appointment_FK_1]
GO
ALTER TABLE [team4].[Appointment]  WITH CHECK ADD  CONSTRAINT [Appointment_FK_2] FOREIGN KEY([doctor_id])
REFERENCES [team4].[Doctor] ([doctor_id])
GO
ALTER TABLE [team4].[Appointment] CHECK CONSTRAINT [Appointment_FK_2]
GO
ALTER TABLE [team4].[Appointment]  WITH CHECK ADD  CONSTRAINT [Appointment_FK_3] FOREIGN KEY([receptionist_id])
REFERENCES [team4].[Receptionist] ([id])
GO
ALTER TABLE [team4].[Appointment] CHECK CONSTRAINT [Appointment_FK_3]
GO
ALTER TABLE [team4].[AppointmentRecord]  WITH CHECK ADD  CONSTRAINT [AppointmentRecord_FK] FOREIGN KEY([record_id])
REFERENCES [team4].[Record] ([record_id])
GO
ALTER TABLE [team4].[AppointmentRecord] CHECK CONSTRAINT [AppointmentRecord_FK]
GO
ALTER TABLE [team4].[AppointmentRecord]  WITH CHECK ADD  CONSTRAINT [AppointmentRecord_FK_1] FOREIGN KEY([appoint_id])
REFERENCES [team4].[Appointment] ([appoint_id])
GO
ALTER TABLE [team4].[AppointmentRecord] CHECK CONSTRAINT [AppointmentRecord_FK_1]
GO
ALTER TABLE [team4].[Comments]  WITH CHECK ADD  CONSTRAINT [Comments_FK] FOREIGN KEY([doctor_id])
REFERENCES [team4].[Doctor] ([doctor_id])
GO
ALTER TABLE [team4].[Comments] CHECK CONSTRAINT [Comments_FK]
GO
ALTER TABLE [team4].[Comments]  WITH CHECK ADD  CONSTRAINT [Comments_FK_1] FOREIGN KEY([patient_id])
REFERENCES [team4].[Patient] ([patient_id])
GO
ALTER TABLE [team4].[Comments] CHECK CONSTRAINT [Comments_FK_1]
GO
ALTER TABLE [team4].[Doctor]  WITH CHECK ADD  CONSTRAINT [Doctor_FK] FOREIGN KEY([clinic_id])
REFERENCES [team4].[Clinic] ([clinic_id])
GO
ALTER TABLE [team4].[Doctor] CHECK CONSTRAINT [Doctor_FK]
GO
ALTER TABLE [team4].[PatientDoctor]  WITH CHECK ADD  CONSTRAINT [PatientDoctor_FK] FOREIGN KEY([doctor_id])
REFERENCES [team4].[Doctor] ([doctor_id])
GO
ALTER TABLE [team4].[PatientDoctor] CHECK CONSTRAINT [PatientDoctor_FK]
GO
ALTER TABLE [team4].[PatientDoctor]  WITH CHECK ADD  CONSTRAINT [PatientDoctor_FK_1] FOREIGN KEY([patient_id])
REFERENCES [team4].[Patient] ([patient_id])
GO
ALTER TABLE [team4].[PatientDoctor] CHECK CONSTRAINT [PatientDoctor_FK_1]
GO
ALTER TABLE [team4].[PendingDeaths]  WITH CHECK ADD  CONSTRAINT [PendingDeaths_FK] FOREIGN KEY([doctor_id])
REFERENCES [team4].[Doctor] ([doctor_id])
GO
ALTER TABLE [team4].[PendingDeaths] CHECK CONSTRAINT [PendingDeaths_FK]
GO
ALTER TABLE [team4].[PendingDeaths]  WITH CHECK ADD  CONSTRAINT [PendingDeaths_FK_1] FOREIGN KEY([patient_id])
REFERENCES [team4].[Patient] ([patient_id])
GO
ALTER TABLE [team4].[PendingDeaths] CHECK CONSTRAINT [PendingDeaths_FK_1]
GO
ALTER TABLE [team4].[Receptionist]  WITH CHECK ADD  CONSTRAINT [Receptionist_FK] FOREIGN KEY([clinic_id])
REFERENCES [team4].[Clinic] ([clinic_id])
GO
ALTER TABLE [team4].[Receptionist] CHECK CONSTRAINT [Receptionist_FK]
GO
ALTER TABLE [team4].[Record]  WITH CHECK ADD  CONSTRAINT [Record_FK] FOREIGN KEY([doctor_id])
REFERENCES [team4].[Doctor] ([doctor_id])
GO
ALTER TABLE [team4].[Record] CHECK CONSTRAINT [Record_FK]
GO
ALTER TABLE [team4].[Record]  WITH CHECK ADD  CONSTRAINT [Record_FK_1] FOREIGN KEY([treatment_id])
REFERENCES [team4].[Treatment] ([treatment_id])
GO
ALTER TABLE [team4].[Record] CHECK CONSTRAINT [Record_FK_1]
GO
ALTER TABLE [team4].[Record]  WITH CHECK ADD  CONSTRAINT [Record_FK_2] FOREIGN KEY([patient_id])
REFERENCES [team4].[Patient] ([patient_id])
GO
ALTER TABLE [team4].[Record] CHECK CONSTRAINT [Record_FK_2]
GO
ALTER TABLE [team4].[Record]  WITH CHECK ADD  CONSTRAINT [Record_FK_3] FOREIGN KEY([condition_id])
REFERENCES [team4].[Condition] ([condition_id])
GO
ALTER TABLE [team4].[Record] CHECK CONSTRAINT [Record_FK_3]
GO
ALTER TABLE [team4].[Treatment]  WITH CHECK ADD  CONSTRAINT [Treatment_FK] FOREIGN KEY([doctor_id])
REFERENCES [team4].[Doctor] ([doctor_id])
GO
ALTER TABLE [team4].[Treatment] CHECK CONSTRAINT [Treatment_FK]
GO
ALTER TABLE [team4].[Treatment]  WITH CHECK ADD  CONSTRAINT [Treatment_FK_1] FOREIGN KEY([drug_id])
REFERENCES [team4].[Drug] ([drug_id])
GO
ALTER TABLE [team4].[Treatment] CHECK CONSTRAINT [Treatment_FK_1]
GO
ALTER TABLE [team4].[Treatment]  WITH CHECK ADD  CONSTRAINT [Treatment_FK_2] FOREIGN KEY([patient_id])
REFERENCES [team4].[Patient] ([patient_id])
GO
ALTER TABLE [team4].[Treatment] CHECK CONSTRAINT [Treatment_FK_2]
GO
/****** Object:  StoredProcedure [dbo].[vale]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[vale]
	@test	nchar(100),
	@extra  nvarchar(100)
AS
BEGIN

	INSERT into [team4].diagrapsemegrigora(test,extra_col)
	values(@test,@extra)


END
GO
/****** Object:  StoredProcedure [team4].[changeAllAccept]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[changeAllAccept]
	@allergyId int
AS
BEGIN
	UPDATE team4.team4.Allergy
	SET accepted = 1
	WHERE allergy_id = @allergyId;
END
GO
/****** Object:  StoredProcedure [team4].[changeDeath]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[changeDeath]
	@deathId int
AS
BEGIN
	
	DECLARE @makaritis AS INT= -1
	PRINT @makaritis
	SELECT @makaritis = patient_id  FROM team4.team4.PendingDeaths WHERE death_id =@deathId;
	
	BEGIN
		DELETE FROM team4.team4.PendingDeaths 
		WHERE death_id =@deathId;
	END
	
	BEGIN
		UPDATE team4.team4.Patient  
		SET alive = 0
		WHERE patient_id= @makaritis;
	END
END
GO
/****** Object:  StoredProcedure [team4].[changeRecAccept]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[changeRecAccept]
	@recId int
AS
BEGIN
	UPDATE team4.team4.Record  
	SET accepted = 1
	WHERE record_id= @recId;
END
GO
/****** Object:  StoredProcedure [team4].[changeTreatAccept]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[changeTreatAccept]
	@treatId int
AS
BEGIN
	UPDATE team4.team4.Treatment 
	SET accepted = 1
	WHERE treatment_id= @treatId;
END
GO
/****** Object:  StoredProcedure [team4].[checkAppointmentRecordConnection]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[checkAppointmentRecordConnection]
	@appoint int
AS 
BEGIN
	SELECT * FROM team4.team4.AppointmentRecord s WHERE s.appoint_id = @appoint
END
GO
/****** Object:  StoredProcedure [team4].[checkIfAlive]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[checkIfAlive]
	@pat_id int
AS 
BEGIN
	SELECT s.alive  FROM team4.team4.Patient s WHERE s.patient_id = @pat_id
	
END
	
GO
/****** Object:  StoredProcedure [team4].[getAllClinics]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getAllClinics]
AS
BEGIN

	SELECT * FROM Clinic

END
GO
/****** Object:  StoredProcedure [team4].[getAllConditions]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getAllConditions]
AS
BEGIN
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT
	*
FROM
	team4.team4.[Condition] c
END
GO
/****** Object:  StoredProcedure [team4].[getAllDoctors]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getAllDoctors]
AS 
BEGIN
	SELECT * FROM team4.team4.Doctor d 
END
GO
/****** Object:  StoredProcedure [team4].[getAllDrugs]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [team4].[getAllDrugs] 
AS
BEGIN
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT * FROM Drug
END
GO
/****** Object:  StoredProcedure [team4].[getAllergy]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getAllergy]
	@allergy_id int
AS 
BEGIN
	SELECT * FROM team4.team4.Allergy WHERE allergy_id = @allergy_id
END
GO
/****** Object:  StoredProcedure [team4].[getAppointmentRecords]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getAppointmentRecords]
	@appoint int
AS 
BEGIN
	SELECT * FROM team4.team4.AppointmentRecord WHERE appoint_id = @appoint
END
GO
/****** Object:  StoredProcedure [team4].[getClinicName]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getClinicName]
	@clinic_id int
	
AS
BEGIN

	SELECT * FROM team4.team4.Clinic c  WHERE c.clinic_id = @clinic_id

END
GO
/****** Object:  StoredProcedure [team4].[getClinics]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getClinics]
AS 
SELECT * FROM Clinic
GO
/****** Object:  StoredProcedure [team4].[getConditionName]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getConditionName]
	@condition int
AS 
BEGIN
	SELECT * FROM team4.team4.Condition WHERE condition_id = @condition
END
GO
/****** Object:  StoredProcedure [team4].[getDaysAppointments]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getDaysAppointments]
	@day date,
	@clinic int
AS
BEGIN
	DECLARE @app_count INT;

SET
	@app_count = (
SELECT
		COUNT(*)
FROM
		team4.team4.Appointment
WHERE
		date = @day
	and
		clinic_id = @clinic
	and attended = 1
	)
	return @app_count
END
GO
/****** Object:  StoredProcedure [team4].[getDeaths]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getDeaths]
AS 
SELECT * FROM team4.team4.PendingDeaths;
GO
/****** Object:  StoredProcedure [team4].[getDoctor]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getDoctor]
	@doctor_id int
	
AS
BEGIN

	SELECT * FROM team4.team4.Doctor d WHERE d.doctor_id = @doctor_id

END
GO
/****** Object:  StoredProcedure [team4].[getDoctorsAppointments]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getDoctorsAppointments]
	@doc int
AS 
BEGIN
	SELECT * FROM team4.team4.Appointment WHERE doctor_id = @doc
END
GO
/****** Object:  StoredProcedure [team4].[getDoctorsAppointmentsForADay]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getDoctorsAppointmentsForADay]
	@doc int,
	@today date
AS 
BEGIN
	SELECT * FROM team4.team4.Appointment WHERE doctor_id = @doc and [date] = @today
END
GO
/****** Object:  StoredProcedure [team4].[getDoctorsOfAClinic]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getDoctorsOfAClinic]
	@clinic int
AS 
BEGIN
	SELECT * FROM team4.team4.Doctor WHERE clinic_id = @clinic
END
GO
/****** Object:  StoredProcedure [team4].[getDoctorsPatient]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getDoctorsPatient]
	@doctorID int 
AS 
BEGIN
	SELECT * FROM PatientDoctor WHERE doctor_id = @doctorID
END
GO
/****** Object:  StoredProcedure [team4].[getDrug]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [team4].[getDrug]
	@searchID int 
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT * FROM Drug WHERE drug_id = @searchID
END
GO
/****** Object:  StoredProcedure [team4].[getIDofInsertedRecord]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getIDofInsertedRecord]
AS
BEGIN
	SELECT
	last_value
FROM
	sys.identity_columns
WHERE
	object_id = OBJECT_ID('Record')
END
GO
/****** Object:  StoredProcedure [team4].[getLastAppointmentID]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getLastAppointmentID]
AS
BEGIN
	SELECT
	last_value
FROM
	sys.identity_columns
WHERE
	object_id = OBJECT_ID('Appointment')
END
GO
/****** Object:  StoredProcedure [team4].[getNumberOfCondition]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getNumberOfCondition]
	@condition int,
	@accepted bit,
	@date nvarchar(100)
AS 
BEGIN
	SELECT * FROM team4.team4.Record WHERE condition_id = @condition and accepted = @accepted and [date] = @date
END
GO
/****** Object:  StoredProcedure [team4].[getNumberOfTreatment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getNumberOfTreatment]
	@drug int,
	@accepted bit,
	@date nvarchar(100)
AS 
BEGIN 
	SELECT * FROM team4.team4.Treatment WHERE drug_id  = @drug and accepted = @accepted and [date] = @date
END
GO
/****** Object:  StoredProcedure [team4].[getPatient]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getPatient]
	@patientID int
AS 
BEGIN

SELECT * FROM team4.team4.Patient p WHERE p.patient_id = @patientID

END
GO
/****** Object:  StoredProcedure [team4].[getPatientAllegies]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getPatientAllegies]
	@patient_id int
AS 
BEGIN
	SELECT * FROM team4.team4.Allergy WHERE patient_id = @patient_id
END
GO
/****** Object:  StoredProcedure [team4].[getPatientAppointmentWithADoctor]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getPatientAppointmentWithADoctor]
	@doc int,
	@pat int
AS 
BEGIN
	SELECT * FROM team4.team4.Appointment WHERE doctor_id = @doc and patient_id = @pat
END
GO
/****** Object:  StoredProcedure [team4].[getPatientByID]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getPatientByID]
	@patient int
AS 
BEGIN 
	SELECT * FROM team4.team4.Patient r WHERE r.patient_id = @patient
END
GO
/****** Object:  StoredProcedure [team4].[getPatientComments]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getPatientComments]
	@patient_id int
AS
BEGIN

	SELECT * FROM team4.team4.Comments c WHERE c.patient_id = @patient_id

END
GO
/****** Object:  StoredProcedure [team4].[getPatientRecords]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getPatientRecords]
	@patient_id int,
	@doctor_id int
AS 
BEGIN
	
	SELECT * FROM Record  r WHERE r.patient_id = @patient_id AND r.doctor_id = @doctor_id
END
GO
/****** Object:  StoredProcedure [team4].[getPatientsConditionTreatment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE	Procedure	[team4].[getPatientsConditionTreatment]
(
	@condition	INT,
	@treatment INT
)

AS
BEGIN
	SET NOCOUNT ON;

	SELECT	*
	FROM	Record AS R, Treatment AS T, Patient P
	WHERE	R.treatment_id = T.treatment_id AND R.condition_id = @condition AND T.drug_id = @treatment AND R.patient_id = T.patient_id AND P.patient_id = T.patient_id


END
GO
/****** Object:  StoredProcedure [team4].[getPatientsLatestAppointment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getPatientsLatestAppointment]
	@pat int
AS 
BEGIN
	SELECT * FROM team4.team4.Appointment d WHERE d.patient_id = @pat and d.attended = 1 ORDER BY date DESC;
END
GO
/****** Object:  StoredProcedure [team4].[getPerscription]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE	Procedure	[team4].[getPerscription]
(
	@patient	int
)

AS
BEGIN
	SET NOCOUNT ON;

	SELECT	TOP 1 *
	FROM	Treatment 
	WHERE	patient_id = @patient 
	ORDER BY treatment_id DESC

END
GO
/****** Object:  StoredProcedure [team4].[getRecordByID]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getRecordByID]
	@rec int
AS 
BEGIN
	SELECT * FROM team4.team4.Record R WHERE R.record_id = @rec
END
GO
/****** Object:  StoredProcedure [team4].[getRequestAllergies]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getRequestAllergies]
AS 
SELECT * FROM team4.team4.Allergy A WHERE A.accepted = 0 ;
GO
/****** Object:  StoredProcedure [team4].[getRequestRecord]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getRequestRecord]
AS 
SELECT * FROM team4.team4.Record R WHERE R.accepted =0;
GO
/****** Object:  StoredProcedure [team4].[getRequestTreatment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getRequestTreatment]
AS 
SELECT * FROM team4.team4.Treatment R WHERE R.accepted =0;
GO
/****** Object:  StoredProcedure [team4].[getTreatment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[getTreatment]
	@treatment int
AS 
BEGIN
	SELECT * FROM team4.team4.Treatment WHERE treatment_id = @treatment
END
GO
/****** Object:  StoredProcedure [team4].[insertAllergy]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[insertAllergy]
	@patient int,
	@drug int,
	@acc bit,
	@date date,
	@last date
AS
BEGIN
	IF EXISTS (
	SELECT
		*
	FROM
		team4.team4.Allergy
	WHERE
		patient_id = @patient
		and drug_id = @drug)
			RETURN
	ELSE
	INSERT
	INTO
	team4.team4.Allergy (patient_id,
	drug_id,
	accepted,
	date,
	last_updated)
VALUES (@patient,
@drug,
@acc,
@date,
@last);
END
GO
/****** Object:  StoredProcedure [team4].[insertAppointment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [team4].[insertAppointment]
(
	@doctor_id	INT,
	@patient_id	INT,
	@clinic_id	INT,
	@date DATE,
	@time NVARCHAR(100),
	@dropin BIT,
	@receptionist_id INT,
	@att BIT
)
AS
BEGIN
	SET NOCOUNT ON;

	BEGIN TRY  
	     
	INSERT	INTO team4.team4.PatientDoctor(doctor_id,patient_id)
	VALUES (@doctor_id,@patient_id)

	END TRY
	BEGIN CATCH
	
	END CATCH


	INSERT INTO Appointment([doctor_id], [patient_id], [clinic_id], [date], [time], [dropIn], [receptionist_id], [attended])
	VALUES (@doctor_id, @patient_id, @clinic_id, @date, @time, @dropin, @receptionist_id, @att)

END
GO
/****** Object:  StoredProcedure [team4].[insertComment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[insertComment]
	@doctor_id int,
	@patient_id int,
	@comment text,
	@in_date date
AS
BEGIN

	INSERT INTO team4.team4.Comments  (patient_id,doctor_id,comment,date) VALUES (@patient_id,@doctor_id,@comment,@in_date);

END
GO
/****** Object:  StoredProcedure [team4].[insertDoctorPatientRelationship]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[insertDoctorPatientRelationship]
	@patient int,
	@doctor int
AS 
BEGIN
	INSERT INTO team4.team4.PatientDoctor  (doctor_id,patient_id) 
	VALUES (@doctor,@patient); 
END
GO
/****** Object:  StoredProcedure [team4].[insertPatient]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[insertPatient]
	@name nvarchar(100),
	@surname nvarchar(100),
	@birth date,
	@email nvarchar(100),
	@tele nvarchar(100)
AS 
BEGIN
INSERT INTO team4.team4.Patient (name,surname,birthday,email,telephone,alive)
	VALUES (@name, @surname, @birth, @email, @tele,1)
END
GO
/****** Object:  StoredProcedure [team4].[insertRecord]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[insertRecord]
	@doctor int,
	@date date,
	@selfharm bit,
	@threat bit,
	@condition int,
	@last date,
	@treatment int,
	@patient int,
	@acc bit,
	@over bit,
	@under bit
AS 
BEGIN	
	INSERT INTO team4.team4.Record  (doctor_id,date,selfharm,threat,overdose,condition_id,last_update,treatment_id,patient_id,accepted,underdose) 
	VALUES (@doctor,@date,@selfharm,@threat,@over,@condition,@last,@treatment,@patient,@acc,@under); 
END
GO
/****** Object:  StoredProcedure [team4].[insertRecordAppointmentRealtion]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[insertRecordAppointmentRealtion]
	@rec int,
	@app int
AS 
BEGIN
	
	INSERT INTO team4.team4.AppointmentRecord  (record_id,appoint_id) VALUES (@rec,@app);
END
GO
/****** Object:  StoredProcedure [team4].[insertTreatment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[insertTreatment]
	@patient int,
	@doctor int,
	@dose int,
	@comment text,
	@warning bit,
	@drug int,
	@acc bit,
	@date date
AS 
BEGIN
	INSERT INTO team4.team4.Treatment (patient_id,doctor_id,dose,comments,warning,drug_id,accepted,date) 
	VALUES (@patient,@doctor,@dose,@comment,@warning,@drug,@acc,@date); 

	PRINT @@IDENTITY
	return @@IDENTITY
END
GO
/****** Object:  StoredProcedure [team4].[loginClinicalStaff]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[loginClinicalStaff]
	@username  nvarchar(100),
	@password  nvarchar(100)
AS
BEGIN

	SELECT * FROM team4.team4.Doctor mrs  WHERE (mrs.username = @username) AND (mrs.password = @password)

END
GO
/****** Object:  StoredProcedure [team4].[loginHealthService]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[loginHealthService]
	@username  nvarchar(100),
	@password  nvarchar(100)
AS
BEGIN

	SELECT * FROM HealthService mrs  WHERE (mrs.username = @username) AND (mrs.password = @password)

END
GO
/****** Object:  StoredProcedure [team4].[loginMedicalRecords]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[loginMedicalRecords]
	@username  nvarchar(100),
	@password  nvarchar(100)
AS
BEGIN

	SELECT * FROM team4.team4.MedicalRecordsStaff mrs  WHERE (mrs.username = @username) AND (mrs.password = @password)

END
GO
/****** Object:  StoredProcedure [team4].[loginReceptionist]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[loginReceptionist]
	@username  nvarchar(100),
	@password  nvarchar(100)
AS
BEGIN

	SELECT * FROM Receptionist mrs  WHERE (mrs.username = @username) AND (mrs.password = @password)

END
GO
/****** Object:  StoredProcedure [team4].[requestDeathOfPatient]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[requestDeathOfPatient]
	@patient int,
	@doctor int,
	@when date
AS 
BEGIN
	INSERT INTO team4.team4.PendingDeaths (patient_id,doctor_id,date) VALUES (@patient,@doctor,@when);
END
GO
/****** Object:  StoredProcedure [team4].[SearchPatient]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE	Procedure	[team4].[SearchPatient]
(
	@SearchPatientName varchar(100)
)

AS
BEGIN
	SET NOCOUNT ON;

	SELECT	*
	FROM	Patient AS P
	WHERE	P.name LIKE '%' + @SearchPatientName + '%' 

END
GO
/****** Object:  StoredProcedure [team4].[ShowAllConditions]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE	Procedure	[team4].[ShowAllConditions]
AS
BEGIN
	SET NOCOUNT ON;

	SELECT	*
	FROM	Condition AS C

END
GO
/****** Object:  StoredProcedure [team4].[ShowAllPatients]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE	Procedure	[team4].[ShowAllPatients]
AS
BEGIN
	SET NOCOUNT ON;

	SELECT	*
	FROM	Patient AS P
	ORDER BY P.patient_id

END
GO
/****** Object:  StoredProcedure [team4].[ShowAllTreatments]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE	Procedure	[team4].[ShowAllTreatments]
	@treatment int
AS
BEGIN
	SET NOCOUNT ON;

	SELECT	TOP 1 *
	FROM	Treatment AS T
	WHERE T.patient_id = @treatment
	ORDER BY treatment_id DESC;

END
GO
/****** Object:  StoredProcedure [team4].[ShowAppointment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE	Procedure	[team4].[ShowAppointment]
(
	@appoint	int
)

AS
BEGIN
	SET NOCOUNT ON;

	SELECT	*
	FROM	team4.Appointment AS APP
	WHERE	APP.appoint_id = @appoint

END
GO
/****** Object:  StoredProcedure [team4].[ShowAppointment_All]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE	Procedure	[team4].[ShowAppointment_All]
(
	@clinic_id	int
)

AS
BEGIN
	SET NOCOUNT ON;

	SELECT	*
	FROM	team4.Appointment AS APP
	WHERE	APP.clinic_id = @clinic_id 
END
GO
/****** Object:  StoredProcedure [team4].[updateAppointment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROC [team4].[updateAppointment]
 @done bit, @appoint_id int
AS
BEGIN
SET NOCOUNT ON
UPDATE [team4].[Appointment]
   SET [attended] = @done
 WHERE Appointment.appoint_id = @appoint_id
 END
GO
/****** Object:  StoredProcedure [team4].[updateRecord]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[updateRecord]
	@record_id int,
	@last_update date,
	@self bit,
	@threat bit,
	@over bit,
	@under bit,
	@cond int,
	@treat int
	
AS 


BEGIN
	
	IF(@treat = -1 )
BEGIN
							UPDATE
	team4.team4.[Record]
SET
	last_update = @last_update,
	selfharm = @self,
	threat = @threat,
	overdose = @over,
	underdose = @under,
	accepted = 0,
	condition_id = @cond,
	treatment_id = NULL
WHERE
	record_id = @record_id;
END
ELSE
BEGIN
				UPDATE
	team4.team4.[Record]
SET
	last_update = @last_update,
	selfharm = @self,
	threat = @threat,
	overdose = @over,
	underdose = @under,
	accepted = 0,
	condition_id = @cond,
	treatment_id = @treat
WHERE
	record_id = @record_id;
END   
	

END
GO
/****** Object:  StoredProcedure [team4].[updateTreatment]    Script Date: 9/5/2022 19:51:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [team4].[updateTreatment]
	@treatment_id int,
	@dose int,
	@comment text,
	@warning int,
	@drug int,
	@last_update date
	
AS 
BEGIN
	UPDATE team4.team4.[Treatment] 
	SET last_update = @last_update, dose = @dose, comments = @comment
	, warning = @warning, drug_id  = @drug, accepted = 0
	WHERE treatment_id = @treatment_id;
END
GO
USE [master]
GO
ALTER DATABASE [team4] SET  READ_WRITE 
GO
