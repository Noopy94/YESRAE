import Song from '../../components/nomantle/Song';

interface ISongInfoProps {
  song: {
    index: number;
    id: string;
    title: string;
    similarity: number;
    rank: number;
    album_img: string;
    answer: boolean;
  }[];
}

export default function SongInfo({ song }: ISongInfoProps) {
  return (
    <div className="w-10/12 ">
      {song.map((item, idx) => {
        return <Song key={idx} songInfoItem={item} />;
      })}
    </div>
  );
}
