import axios from 'axios';

const BASE_URL = 'http://localhost:8080/';

export const getComment = async (type: string, typeId: number, token: string) => {
  const response = await axios.get(BASE_URL + type + 'Comment/' + typeId, {
    headers: {
      Authorization: 'Bearer ' + token,
    },
  });
  if (response.data.success) {
    return response.data.content;
  } else {
    alert(response.data.error.message);
    return null;
  }
};

export const getDate = (createdAt: string): string => {
  const date = new Date(createdAt);
  return date.getFullYear() + '.' + ((date.getMonth() + 1) < 9 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)) + '.' + ((date.getDay() + 1) < 9 ? '0' + (date.getDay() + 1) : (date.getDay() + 1)) + ' ' + (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':' + (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':' + (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
};

export const registPlaylistComment = async (typeId: number, userId: number, content: string, token: string) => {
  const response = await axios.post(BASE_URL + 'playlistComment', {
    userId: userId,
    content: content,
    playlistId: typeId,
  }, {
    headers: {
      Authorization: 'Bearer ' + token,
    },
  });
  if (response.data.success) {
    return true;
  } else {
    alert(response.data.error.message);
    return false;
  }
};

export const registArticleComment = async (typeId: number, userId: number, content: string) => {
  const response = await axios.post(BASE_URL + 'articleComment', {
    userId: userId,
    content: content,
    articletId: typeId,
  });
  if (response.data.success) {
    return true;
  } else {
    alert(response.data.error.message);
    return false;
  }
};

export const deletePlaylistComment = async (commentId: number, userId: number, token: string) => {
  const response = await axios.put(BASE_URL + 'playlistComment/delete', {
    userId: userId,
    id: commentId,
  }, {
    headers: {
      Authorization: 'Bearer ' + token,
    },
  });
  if (response.data.success) {
    alert('삭제가 완료되었습니다.');
    history.go(0);
  } else {
    alert(response.data.error.message);
  }
};

export const deleteArticleComment = async (commentId: number, userId: number, token: string) => {
  const response = await axios.put(BASE_URL + 'articleComment/delete', {
    userId: userId,
    id: commentId,
  }, {
    headers: {
      Authorization: 'Bearer ' + token,
    },
  });
  if (response.data.success) {
    alert('삭제가 완료되었습니다.');
    history.go(0);
  } else {
    alert(response.data.error.message);
  }
};

export const getNotification = async (userId: number) => {
  const response = await axios.get(BASE_URL + 'notification/user/' + userId);
  if (response.data.success) {
    return response.data.content;
  } else {
    alert(response.data.error.message);
    return null;
  }
};

export const findNotification = async (notificationId: number) => {
  const response = await axios.get(BASE_URL + 'notification/' + notificationId);
  if (!response.data.success) {
    alert(response.data.error.message);
  }
};

export const registNotification = async (type: 'playlistComment' | 'articleComment', target: string, senderName: string, recipientId: number, token: string) => {
  let title = '';
  let content = '';
  if (type === 'articleComment') {
    title = '게시글에 댓글이 달렸습니다.';
    content = { target } + ' 게시글에 ' + { senderName } + ' 님이 댓글을 다셨습니다.';
  } else if (type === 'playlistComment') {
    title = '플레이리스트에 댓글이 달렸습니다.';
    content = { target } + ' 플레이리스트에 ' + { senderName } + ' 님이 댓글을 다셨습니다.';
  }
  const response = await axios.post(BASE_URL + 'notification', {
    recipientId: recipientId,
    content: content,
    title: title,
  }, {
    headers: {
      Authorization: 'Bearer ' + token,
    },
  });
  if (!response.data.success) {
    alert(response.data.error.message);
  }
};