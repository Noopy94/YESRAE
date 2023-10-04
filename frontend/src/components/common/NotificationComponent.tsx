import { useEffect, useState } from 'react';
import axios from 'axios';

interface Notification {
  title: string,
  content: string,
  createdAt: string,
  isViewed: boolean
}

interface NotificationProps {
  userId: number;
}

const notifications: Notification[] = [];
export default function NotificationComponent({ userId }: NotificationProps) {

  const [currentNotifiactionList, setCurrentNotificationList] = useState(notifications);

  useEffect(() => {
    axios.get('http://localhost:8080/notification/user/' + userId)
    .then((response) => {
      setCurrentNotificationList(response.data.content);
    })
    .catch((error) => {
      console.error('Error fetching Notification data:', error);
    });
  }, []);

  const getDate = (createdAt: string) => {
    const date = new Date(createdAt);
    return date.getFullYear() + '-' + date.getMonth() + '-' + date.getDay() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
  };

  return (
    <div>
      {currentNotifiactionList.map((notification: Notification, index: number) => (
        <div key={index}
             className={notification.isViewed === true ? 'text-gray-500' : 'text-white-900'}>
          <div>제목 : {notification.title}</div>
          <br />
          <div>내용 : {notification.content}</div>
          <br />
          <div>작성 시간 : {getDate(notification.createdAt)}</div>
        </div>
      ))}
    </div>
  );
}