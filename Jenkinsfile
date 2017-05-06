#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('check java') {
            steps {
                sh "java -version"
            }
        }

        stage('clean') {
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew clean --no-daemon"
            }
        }

        stage('backend tests') {
            steps {
                try {
                    sh "./gradlew test -PnodeInstall --no-daemon"
                } catch(err) {
                    throw err
                } finally {
                    junit '**/build/**/TEST-*.xml'
                }
            }
        }

        stage('packaging') {
            steps {
                sh "./gradlew bootRepackage -x test -Pprod -PnodeInstall --no-daemon"
                archiveArtifacts artifacts: '**/build/libs/*.war', fingerprint: true
            }
        }

        def dockerImage
        stage('build docker') {
            steps {
                 sh "cp -R src/main/docker build/"
                sh "cp build/libs/*.war build/docker/"
                dockerImage = docker.build('ainguyen/gsite-micro-customer', 'build/docker')
            }
        }

        stage('publish docker') {
            steps {
                docker.withRegistry('https://registry.hub.docker.com', 'docker-login') {
                    dockerImage.push 'latest'
                }
            }
        }
    }
}
