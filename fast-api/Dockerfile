# 기반 이미지를 선택합니다 (예: Python)
FROM python:3.10

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 애플리케이션 의존성 설치
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt

# 애플리케이션 소스 코드 복사
COPY . .

# FastAPI 애플리케이션 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "81"]
