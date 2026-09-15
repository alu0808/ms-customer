pipeline {
    agent any

    // Le decimos a Jenkins qué herramientas usar de las que configuramos
    tools {
        maven 'Maven3'
    }

    stages {
        stage('1. Compilación') {
            steps {
                echo 'Compilando el microservicio...'
                // clean verify asegura que compile y corra las pruebas unitarias si las hay
                sh 'mvn clean verify -DskipTests'
            }
        }

        stage('2. Análisis SAST (SonarQube)') {
                    steps {
                        echo 'Enviando código al inspector de calidad (Bypass Explícito)...'
                        withSonarQubeEnv('sonarqube-bcp') {
                            // Forzamos explícitamente la URL y el Token en el comando de Maven.
                            // Además, añadimos -X para tener logs detallados (Modo Debug) si vuelve a fallar.
                            sh 'mvn sonar:sonar -Dsonar.projectKey=ms-customer -Dsonar.projectName=ms-customer -Dsonar.host.url=http://sonarqube_bcp:9000 -Dsonar.token=$SONAR_AUTH_TOKEN -X'
                        }
                    }
                }

        stage('3. Quality Gate (Puerta de Calidad)') {
            steps {
                echo 'Esperando el veredicto de SonarQube...'
                // Jenkins pausará aquí. Si SonarQube dice que el código es inseguro, el pipeline se aborta.
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('4. Empaquetado (Docker)') {
            steps {
                echo 'El código es seguro. Armando la imagen Docker...'
                sh 'docker build -t banco-bcp/ms-customer:latest .'
            }
        }
    }
}