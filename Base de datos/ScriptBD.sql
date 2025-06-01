CREATE TABLE semestre(
idSemestre INT PRIMARY KEY,
semestre TEXT
);

CREATE TABLE carrera(
idCarrera INT PRIMARY KEY,
nombreCarrera TEXT,
tipoCarrera TEXT
);

CREATE TABLE materia(
idMateria INT PRIMARY KEY,
nombreMateria TEXT,
creditos INT,
horasAula INT,
horasIndep INT
);

CREATE TABLE planEstudios(
idPlan INT PRIMARY KEY,
ciclo TEXT,
obligatoria BOOLEAN,
idSemestre INT,
idMateria INT,
idCarrera INT,
FOREIGN KEY(idSemestre) REFERENCES semestre(idSemestre) ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY(idMateria) REFERENCES materia(idMateria) ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY(idCarrera) REFERENCES carrera(idCarrera) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE estudiantes(
matricula VARCHAR(10) PRIMARY KEY,
nomEst TEXT,
apellidoPEst VARCHAR(50),
apellidoMEst VARCHAR(50),
numTelEst VARCHAR(10),
correoEst VARCHAR(50),
idCarrera INT,
idSemestre INT,
FOREIGN KEY(idCarrera) REFERENCES carrera(idCarrera) ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY(idSemestre) REFERENCES semestre(idSemestre) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE profesor(
idProfesor VARCHAR(10) PRIMARY KEY,
nomProf TEXT,
apellidoPProf VARCHAR(50),
apellidoMProf VARCHAR(50),
numTelProf VARCHAR(10),
correoProf VARCHAR(50),
especialidad TEXT,
descProf TEXT,
cargoActual TEXT,
estatusProf TEXT
);

CREATE TABLE usuarios(
idUsuario INT PRIMARY KEY,
usuario TEXT,
userPassword TEXT,
rol TEXT,
idProfesor VARCHAR(10),
FOREIGN KEY(idProfesor) REFERENCES profesor(idProfesor) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE materiaProf(
idMProf INT PRIMARY KEY,
idMateria INT,
idProfesor VARCHAR(10),
FOREIGN KEY(idMateria) REFERENCES materia(idMateria) ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY(idProfesor) REFERENCES profesor(idProfesor) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE institutos(
idInstituto INT PRIMARY KEY,
nombreInstituto TEXT,
ubicacion TEXT,
telefonoInstituto VARCHAR(10)
);

CREATE TABLE cubos(
idCubo INT PRIMARY KEY,
numeroCubo INT,
estadoCubo TEXT,
idInstituto INT,
FOREIGN KEY(idInstituto) REFERENCES institutos(idInstituto) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE histoAsignInst(
idHistoAsignInst INT PRIMARY KEY,
fechaAsign DATE,
motivoAsign TEXT,
idProfesor VARCHAR(10),
idCubo INT,
FOREIGN KEY(idProfesor) REFERENCES profesor(idProfesor) ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY(idCubo) REFERENCES cubos(idCubo) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE horarioAsesoria(
idHorario INT PRIMARY KEY,
fecha DATE,
horaInicio TIME,
horaFin TIME
);

CREATE TABLE asesorias(
idAsesoria INT PRIMARY KEY,
observaciones TEXT,
sustucion TEXT,
idProfesor VARCHAR(10),
idMateria INT,
idHorario INT,
FOREIGN KEY(idProfesor) REFERENCES profesor(idProfesor) ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY(idMateria) REFERENCES materia(idMateria) ON UPDATE CASCADE ON DELETE RESTRICT,
FOREIGN KEY(idHorario) REFERENCES horarioAsesoria(idHorario) ON UPDATE CASCADE ON DELETE RESTRICT
);
ALTER TABLE usuarios ALTER COLUMN idusuario SET DEFAULT nextval('usuarios_idusuario_seq');
CREATE SEQUENCE IF NOT EXISTS usuarios_idusuario_seq;