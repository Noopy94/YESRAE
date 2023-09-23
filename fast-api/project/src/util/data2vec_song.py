import os
from transformers import AutoModel
import torch

from song_analyze import getMusic, loadmusic

# 음원 파일 저장하는 경로
root_path = os.getcwd() +"\\"

today_song_url = "https://p.scdn.co/mp3-preview/123dc7888494074e6e21cf643ec767448cdf3978?cid=c551bf26249e4acf9eab170aed614fab"
# today_song_url = "https://p.scdn.co/mp3-preview/afb3b416f80fe7e3a504fd809af164fd6199f7aa?cid=b76e1a72191a49e1bd4cc3b5aaa2511b"

today_song_file = root_path + getMusic(today_song_url)
y1, sr1 = loadmusic(today_song_file)

print(y1)

y_tensor = torch.tensor(y1)
y_tensor_len = y_tensor.size(0)
y_tensor = y_tensor[:y_tensor_len//3]
print(y_tensor.shape)
y_tensor = y_tensor.unsqueeze(0)
print(y_tensor.shape)
# checkpoint = 'arxyzan/data2vec-roberta-base'
checkpoint = "facebook/wav2vec2-base"

# Option 1: load using AutoModel
model = AutoModel.from_pretrained(checkpoint)

with torch.no_grad():
    result = model.forward(y_tensor)

    print(f"last_hidden_state {result['last_hidden_state']}")
    print(f"shape : {result['last_hidden_state'].shape}")

    result['last_hidden_state'] = result['last_hidden_state'].squeeze(0)
    print(f"last_hidden_state {result['last_hidden_state']}")
    print(f"shape : {result['last_hidden_state'].shape}")

