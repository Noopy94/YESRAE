import logging

logging.basicConfig(
    filename="yesrae.log",
    level=logging.INFO, 
    format='%(asctime)s - %(levelname)s - %(message)s',
    encoding='utf-8'
)


# # 로거 생성
# logger = logging.getLogger(__name__)
# logger.setLevel(logging.INFO)  # 로그 레벨 설정

# # 터미널 출력을 위한 StreamHandler 생성
# ch = logging.StreamHandler()
# ch.setLevel(logging.INFO)  # 로그 레벨 설정

# # 파일 출력을 위한 FileHandler 생성
# fh = logging.FileHandler('yesrae.log')
# fh.setLevel(logging.INFO)  # 로그 레벨 설정

# # 포맷터 설정
# formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
# ch.setFormatter(formatter)
# fh.setFormatter(formatter)

