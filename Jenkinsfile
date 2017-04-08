pipeline {
    agent any
    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('clean') {
            steps {
                sh "./gradlew clean"
            }
        }
        
        stage('build') {
        steps {
            parallel(
            "build": {
                sh './gradlew build -Pprod'
                
            },
            "test": {
                sh './gradlew test'
                
            }
            )
        }
        }

        stage('imaging') {
            steps {
                sh "docker build -t ainguyen/gsite-micro-customer ./build/docker/"
            }
        }

        stage('pushing image') {
            steps {
                sh "docker tag ainguyen/gsite-micro-customer gsite.cf/gsite-micro-customer"
                sh "docker push gsite.cf/gsite-micro-customer"
            }
        }
    }
}
