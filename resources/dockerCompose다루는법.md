- docker compose up -d : 컨테이너를 백그라운드로 실행합니다.
- docker compose ps : 실행중인 컨테이너 목록을 확인합니다.
- docker compose logs -f : 컨테이너 로그를 확인합니다.

~~~
PostgreSQL (postgres): postgres 이미지 기반의 PostgreSQL 데이터베이스 컨테이너로, 사용자 이름과 비밀번호를 설정했으며, 데이터는 postgres 볼륨에 저장되어 영구적으로 유지돼. 5432:5432 포트가 열려있어 호스트에서 데이터베이스에 접근 가능하게 했어.

PgAdmin (pgadmin): dpage/pgadmin4 이미지를 사용하여 PostgreSQL을 관리할 수 있는 PgAdmin 컨테이너야. 5050:80 포트를 통해 브라우저에서 접속 가능하고, 기본 관리자 이메일과 비밀번호를 환경 변수로 설정했어.

MongoDB (mongodb): MongoDB 데이터베이스 컨테이너로, 사용자 이름과 비밀번호가 환경 변수로 지정되어 있으며, 데이터는 mongo 볼륨에 영구 저장돼. 27017:27017 포트로 접근할 수 있어.

Mongo Express (mongo-express): MongoDB 관리용 웹 인터페이스를 제공하는 mongo-express 컨테이너로, 8081:8081 포트로 접근할 수 있어.

MailDev (mail-dev): 테스트 이메일 서버를 위해 maildev 이미지를 사용한 컨테이너로, SMTP 테스트를 위한 포트 1025와 웹 인터페이스를 제공하는 1080 포트를 열어 두었어.

네트워크 및 볼륨:

네트워크: microservices-net이라는 커스텀 네트워크를 정의해 컨테이너 간 통신을 가능하게 했어.
볼륨: postgres, pgadmin, mongo 볼륨이 각 데이터 저장소로 설정되어 컨테이너 재시작 시 데이터가 유지되도록 했어.

~~~