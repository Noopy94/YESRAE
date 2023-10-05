import { useEffect, useState } from 'react';
import {
  deleteArticleComment,
  deletePlaylistComment,
  getComment,
  getDate,
  getName,
} from '../../api/commentApi.ts';
import { userState } from '../../recoil/user/user.ts';
import { useRecoilValue } from 'recoil';

interface Comment {
  id: number,
  nickName: string;
  userId: number,
  content: string,
  createdAt: string,
}

interface CommentProps {
  type: 'article' | 'playlist',
  typeId: number,
  loading: (arg: boolean) => void;
}

const comments: Comment[] = [];
export default function CommentComponent({ type, typeId, loading }: CommentProps) {

  const user = useRecoilValue(userState);
  const [currentCommentList, setCurrentCommentList] = useState(comments);

  const onChangeCurrentCommentList = (data: Comment[]) => {
    setCurrentCommentList(data);
  };

  const onChangeLoading = () => {
    loading(true);
  };

  useEffect(() => {
    getComment(type, typeId).then((res) => {
      res.map((comment: Comment) => {
        getName(comment.userId).then((nickname) => {
          comment.nickName = nickname;
        });
      });
      onChangeCurrentCommentList(res);
    }).then(onChangeLoading);
  }, []);

  const deleteComment = (commentId: number) => {
    if (confirm('정말로 댓글을 삭제하시겠습니까?')) {
      if (type === 'playlist') {
        deletePlaylistComment(commentId, user.id);
      } else if (type === 'article') {
        deleteArticleComment(commentId, user.id);
      }
    }
  };

  return (
    <div className='bg-white text-black w-4/5 min-h-fit'>
      {currentCommentList.map((comment: Comment, index: number) => (
        <div key={index} className='p-5 pt-0'>
          <div className='pb-3'>
            <div className='pb-5'>
              <div className='inline pr-3'>{comment.nickName}</div>
              <div className='inline text-xs'>|</div>
              <div className='inline pl-3 text-xs'>{getDate(comment.createdAt)}</div>
              {comment.userId === user.id ?
                <button className='float-right'
                        onClick={() => deleteComment(comment.id)}>삭제하기</button> :
                <div />}
            </div>
            <div>{comment.content}</div>
          </div>
          <br />
          <hr />
        </div>
      ))}
    </div>
  );
}