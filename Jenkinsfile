node {
    stage('checkout') {
        checkout scm
    }

    stage('clean') {
        sh "./gradlew clean"
    }

    stage('backend tests') {
        try {
            sh "./gradlew test"
        } catch(err) {
            throw err
        }
    }

    stage('packaging') {
        sh "./gradlew bootRepackage -Pprod -x test"
    }

    stage('imaging') {
        sh "./gradlew bootRepackage -Pprod buildDocker"
    }

    stage('pushing') {
        sh "sudo docker push ainguyen/gsite-micro-customer"
    }
}
