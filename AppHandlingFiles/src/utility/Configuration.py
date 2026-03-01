import os
from dotenv import load_dotenv
load_dotenv()

class Configuration:
    ENDPOINT=os.getenv("ENDPOINT")
    KEY=os.getenv("SUBSCRIPTION_KEY")
    STORAGE_CONNECTION=os.getenv("STORAGE_CONNECTION_ST")
    CONTAINER =os.getenv("CONTAINER_NAME")


def STORAGE_CONNECTION():
    return None