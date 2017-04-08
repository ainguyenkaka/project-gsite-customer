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
        sh "docker image build -f build/docker/Dockerfile -t ainguyen/gsite-micro-customer"
    }

    stage('pushing image') {
        sh "docker tag ainguyen/gsite-micro-customer gsite.cf/gsite-micro-customer"
        sh "docker push gsite.cf/gsite-micro-customer"
    }

}
