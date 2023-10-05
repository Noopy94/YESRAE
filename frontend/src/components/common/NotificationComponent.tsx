import { useEffect, useState } from 'react';
import { findNotification, getDate, getNotification } from '../../api/commentApi.ts';

interface Notification {
  id: number,
  title: string,
  content: string,
  createdAt: string,
  isViewed: boolean
}

interface NotificationProps {
  userId: number;
  loading: (arg: boolean) => void;
}

export default function NotificationComponent({ userId, loading }: NotificationProps) {

  const [currentNotifiactionList, setCurrentNotificationList] = useState([]);

  useEffect(() => {
    getNotification(userId).then((res) => setCurrentNotificationList(res));
  }, []);

  const onChangeLoading = () => {
    loading(true);
  };

  const readNotification = (notifiactionId: number) => {
    findNotification(notifiactionId).then(onChangeLoading);
  };

  return (
    <div className='bg-white w-4/5'>
      {currentNotifiactionList.map((notification: Notification, index: number) => (
        <div key={index} className='p-5 cursor-pointer'>
          <div
            className={notification.isViewed === true ? 'text-gray-500' : 'text-black'}
            onClick={() => readNotification(notification.id)}>
            <div className='py-3'>
              <div className='inline pr-5'>{notification.title}</div>
              <div className='inline float-right text-xs'>{getDate(notification.createdAt)}</div>
            </div>
            <div className='pt-7'>{notification.content}</div>
          </div>
          <br />
          <hr />
        </div>
      ))}
    </div>
  );
}