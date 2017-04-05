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
        sh "sudo docker tag ainguyen/gsite-micro-customer localhost:5000/gsite-micro-customer"
        sh "sudo docker push localhost:5000/gsite-micro-customer"
    }

     stage('updating service') {
        sh "sudo docker service update gscloud_gsite-customer"
    }
}
