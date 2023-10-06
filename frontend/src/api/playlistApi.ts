import axios, { AxiosError, AxiosResponse } from 'axios';
import { findnickname } from './userApi';
import { PlayList } from '../recoil/defaultdata/data';

//const BASE_URL = 'https://i9a304.p.ssafy.io/api';

const BASE_URL = 'http://localhost:8080/playlist';

// Playlist 조회 Api
export async function findPlaylistApi(Id: number) {
  try {
    const apiUrl = BASE_URL + '/find/' + Id;
    const response: AxiosResponse = await axios.get(apiUrl);

    console.log('성공:', response.data.conetent);
    return response.data.content;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}

//Playlist 안에 있는 노래 조회 Id는 플레이 리스트 아이디
export async function findSonglistApi(Id: number) {
  try {
    const apiUrl = BASE_URL + '/songs/' + Id;
    const response: AxiosResponse = await axios.get(apiUrl);
    console.log('성공:', response.data.content);
    return response.data.content;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}

//Playlist 안에 있는 태그 조회 Id는 플레이 리스트 아이디
export async function findTagsApi(Id: number) {
  try {
    const apiUrl = BASE_URL + '/tags/' + Id;
    const response: AxiosResponse = await axios.get(apiUrl);
    console.log('성공:', response.data);
    return response.data;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}

//태그 검색시 플레이 리스트 반환

export interface PlaylistSearchGetReq {
  keyword: String;
  page: number; //페이지네이션은 0부터 시작
}

export async function findPlaylistByTagApi(
  playlistSearchGetReq: PlaylistSearchGetReq,
) {
  try {
    const apiUrl = BASE_URL + '/findtag';
    const response: AxiosResponse<{ content: PlaylistGetResponse[] }> =
      await axios.post(apiUrl, playlistSearchGetReq);

    const playlists: PlayList[] = [];

    for (const playlist of response.data.content) {
      const userNickName = await findnickname(playlist.userId);

      const playList: PlayList = {
        playListId: playlist.id,
        playListUserId: playlist.userId,
        playListUserNickName: userNickName,
        playListTitle: playlist.title,
        playListDescription: playlist.description,
        playListImageUrl: playlist.imgUrl,
      };

      playlists.push(playList);
    }

    return playlists;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}

//제목 검색시 플레이 리스트 반환
export async function findPlaylistByTitleApi(
  playlistSearchGetReq: PlaylistSearchGetReq,
) {
  try {
    const apiUrl = BASE_URL + '/findtitle';
    const response: AxiosResponse<{ content: PlaylistGetResponse[] }> =
      await axios.post(apiUrl, playlistSearchGetReq);
    console.log('성공:', response.data.content);
    const playlists: PlayList[] = [];

    for (const playlist of response.data.content) {
      const userNickName = await findnickname(playlist.userId);

      const playList: PlayList = {
        playListId: playlist.id,
        playListUserId: playlist.userId,
        playListUserNickName: userNickName,
        playListTitle: playlist.title,
        playListDescription: playlist.description,
        playListImageUrl: playlist.imgUrl,
      };

      playlists.push(playList);
    }

    return playlists;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}

export interface PlaylistGetResponse {
  id: number;
  userId: number;
  title: string;
  description: string;
  viewCount: number;
  likeCount: number;
  imgUrl: string;
  tags: string[];
}

// 베스트 20개 플레이 리스트 가져오기
export async function findBest20LikeCntPlaylistApi() {
  try {
    const apiUrl = BASE_URL + '/best20likecnt';
    const response: AxiosResponse<{ content: PlaylistGetResponse[] }> =
      await axios.get(apiUrl);
    console.log('좋아요 베스트 가져오기 성공:', response.data);

    const playlists: PlayList[] = [];

    for (const playlist of response.data.content) {
      const userNickName = await findnickname(playlist.userId);

      const playList: PlayList = {
        playListId: playlist.id,
        playListUserId: playlist.userId,
        playListUserNickName: userNickName,
        playListTitle: playlist.title,
        playListDescription: playlist.description,
        playListImageUrl: playlist.imgUrl,
      };

      playlists.push(playList);
    }

    return playlists;
  } catch (error) {
    console.error(
      '조회수 베스트 가져오기 실패:',
      (error as AxiosError).message,
    );
    throw error;
  }
}

export async function findBest20ViewCntPlaylistApi() {
  try {
    const apiUrl = BASE_URL + '/best20viewcnt';
    const response: AxiosResponse<{ content: PlaylistGetResponse[] }> =
      await axios.get(apiUrl);
    console.log('조회수 베스트 가져오기 성공:', response.data);

    const playlists: PlayList[] = [];

    for (const playlist of response.data.content) {
      const userNickName = await findnickname(playlist.userId);
      const playList: PlayList = {
        playListId: playlist.id,
        playListUserId: playlist.userId,
        playListUserNickName: userNickName,
        playListTitle: playlist.title,
        playListDescription: playlist.description,
        playListImageUrl: playlist.imgUrl,
      };

      playlists.push(playList);
    }

    return playlists;
  } catch (error) {
    console.error(
      '조회수 베스트 가져오기 실패:',
      (error as AxiosError).message,
    );
    throw error;
  }
}

//유저 플레이 리스트 가져오기
export async function findUserPlayListApi(Id: number) {
  try {
    const apiUrl = BASE_URL + '/user/' + Id;
    const response: AxiosResponse<{ content: PlaylistGetResponse[] }> =
      await axios.get(apiUrl);
    console.log('유저 플레이 리스트 가져오기 성공:', response.data);
    const playlists: PlayList[] = [];

    for (const playlist of response.data.content) {
      const userNickName = await findnickname(Id);
      const playList: PlayList = {
        playListId: playlist.id,
        playListUserId: playlist.userId,
        playListUserNickName: userNickName,
        playListTitle: playlist.title,
        playListDescription: playlist.description,
        playListImageUrl: playlist.imgUrl,
      };

      playlists.push(playList);
    }

    return playlists;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}

//유저 팔로워 플레이 리스트 가져오기
export async function findFollowerPlayListApi(Id: number) {
  try {
    const apiUrl = BASE_URL + '/follwerplaylist/' + Id;
    const response: AxiosResponse<{ content: PlaylistGetResponse[] }> =
      await axios.get(apiUrl);
    console.log('유저 팔로우 플레이 리스트 가져오기 성공:', response.data);

    const playlists: PlayList[] = [];

    for (const playlist of response.data.content) {
      const userNickName = await findnickname(Id);
      const playList: PlayList = {
        playListId: playlist.id,
        playListUserId: playlist.userId,
        playListUserNickName: userNickName,
        playListTitle: playlist.title,
        playListDescription: playlist.description,
        playListImageUrl: playlist.imgUrl,
      };

      playlists.push(playList);
    }

    return playlists;
  } catch (error) {
    console.error('실패:', (error as AxiosError).message);
  }
}
