import { AxiosError, AxiosResponse } from 'axios';
import axios from './securityIndex';

export interface PlayListRegistPostReq {
  userId: number;
  isPublic: number;
  title: string;
  description: string;
  tags: string[];
}

// Playlist 등록 Api
export async function registPlaylistApi(
  playlistRegistPostReq: PlayListRegistPostReq,
  file: File | null,
) {
  try {
    const apiUrl = 'playlist';
    const formData = new FormData();
    const json = JSON.stringify(playlistRegistPostReq);
    const blob = new Blob([json], { type: 'application/json' });
    formData.append('playlistRegistPostReq', blob);

    if (file) {
      formData.append('file', file);
    }

    console.log('플레이 리스트 등록 api 시작');

    const response = await axios.post(apiUrl, formData);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('실패:', error as AxiosError);
  }
}

export async function updatePlaylistApi(
  playlistRegistPostReq: PlayListRegistPostReq,
  file: File | null,
) {
  try {
    const apiUrl = 'playlist';
    const formData = new FormData();
    const json = JSON.stringify(playlistRegistPostReq);
    const blob = new Blob([json], { type: 'application/json' });
    formData.append('playlistRegistPostReq', blob);

    if (file) {
      formData.append('file', file);
    }

    const response = await axios.put(apiUrl, formData);

    return response.data;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}

export interface PlaylistLikeRegistPostReq {
  userId: number;
  playlistId: number;
}

// 해당 플레이 리스트 좋아하는지 아닌지만 확인
export async function findPlayListLikeApi(
  playlistLikeRegistPostReq: PlaylistLikeRegistPostReq,
) {
  try {
    const apiUrl = 'playlist/playlistlike';

    const response: AxiosResponse = await axios.post(
      apiUrl,
      playlistLikeRegistPostReq,
    );
    console.log('성공:', response.data);
    return response.data;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}
