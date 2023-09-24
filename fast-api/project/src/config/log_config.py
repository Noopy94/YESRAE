import logging

logging.basicConfig(
    filename='yesrae.log',
    # 로그 레코드 생성된 시간, 로거 이름, 로그 레벨 메세지
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    level='INFO'
)