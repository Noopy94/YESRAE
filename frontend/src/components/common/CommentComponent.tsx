import { useEffect, useState } from 'react';
import axios from 'axios';

interface Comment {
  userId: number,
  content: string,
  createdAt: string,
}

interface CommentProps {
  type: 'article' | 'playList',
  typeId: number,
}

let URL: string;
const comments: Comment[] = [];
export default function CommentComponent({ type, typeId }: CommentProps) {

  const [currentCommentList, setCurrentCommentList] = useState(comments);

  if (type === 'article') {
    URL = `http://localhost:8080/articleComment/`;
  } else if (type === 'playList') {
    URL = `http://localhost:8080/playlistComment/`;
  }

  useEffect(() => {
    axios.get(URL + typeId)
    .then((response) => {
      setCurrentCommentList(response.data.content);
    })
    .catch((error) => {
      console.error('Error fetching Comment data:', error);
    });
  }, []);

  const getDate = (createdAt: string) => {
    const date = new Date(createdAt);
    return date.getFullYear() + '-' + date.getMonth() + '-' + date.getDay() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
  };

  return (
    <div>
      {currentCommentList.map((comment: Comment, index: number) => (
        <div key={index}>
          <div>작성자 : {comment.userId}</div>
          <br />
          <div>내용 : {comment.content}</div>
          <br />
          <div>작성 시간 : {getDate(comment.createdAt)}</div>
        </div>
      ))}
    </div>
  );
}