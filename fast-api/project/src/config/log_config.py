import logging

logging.basicConfig(
    filename = "yesrae.log",
    level=logging.INFO, 
    format='%(asctime)s - %(levelname)s - %(message)s',
    encoding='utf-8'
)


# 터미널 출력 로그 
console_handler = logging.StreamHandler()
console_handler.setLevel(logging.INFO)  # 터미널에 출력할 로그 레벨 설정

formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
console_handler.setFormatter(formatter)

# 루트 로거에 핸들러 추가
root_logger = logging.getLogger()
root_logger.addHandler(console_handler)

