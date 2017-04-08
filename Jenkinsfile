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
        sh "docker build -t ainguyen/gsite-micro-customer build/docker/"
    }

    stage('pushing image') {
        sh "docker tag ainguyen/gsite-micro-customer gsite.cf/gsite-micro-customer"
        sh "docker push gsite.cf/gsite-micro-customer"
    }

}
