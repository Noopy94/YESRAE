interface ProgressProps{
  amount:number,
  max:number;
}

export default function ProgressComponent({amount,max}:ProgressProps){
  return(
    <div className='text-sm inline'>
      <div className='text-black inline-block bg-blue-gray-100 mx-3 align-middle w-4/5 h-8'>
        <div className='bg-gradient-to-r from-yesrae-0 to-yesrae-100 mr-3 h-8 inline-block align-middle content-end' style={{width:amount*100/max+'%'}}/>
        {amount}
      </div>
        {max}
    </div>

  )
}