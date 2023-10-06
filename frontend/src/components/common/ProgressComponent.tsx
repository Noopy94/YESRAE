interface ProgressProps {
  amount: number,
  max: number;
}

export default function ProgressComponent({ amount, max }: ProgressProps) {
  return (
    <div className='text-sm pt-1'>
      <div
        className='text-black inline-block bg-blue-gray-100 align-middle w-4/5 h-8 rounded-md mr-5'>
        <div
          className='bg-gradient-to-r from-yesrae-0 to-yesrae-100 pr-3 h-8 inline-block align-middle content-end rounded-md mr-2'
          style={{ width: amount * 100 / max + '%' }} />
        {amount}
      </div>
      {max}
    </div>
  );
}