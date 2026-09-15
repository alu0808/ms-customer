pipeline {
    // Le decimos a Jenkins que use cualquier agente disponible
    agent any

    stages {
        stage('Validar Código') {
            steps {
                echo 'Verificando que el código compila...'
                // Aquí más adelante conectaremos a SonarQube
            }
        }

        stage('Empaquetar con Docker') {
            steps {
                echo 'Construyendo la imagen Docker para ms-customer...'
                // Jenkins leerá tu Dockerfile y armará la caja
                sh 'docker build -t banco-bcp/ms-customer:latest .'
            }
        }
    }
}