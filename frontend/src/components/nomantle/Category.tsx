
interface ICategoryProps{
    categories: string[];
}

export default function Category({
    categories
}: ICategoryProps){
    return(
        <div className="flex items-center text-center mb-7">
            {categories.map((item, idx) => (
                <div key = {idx} className="flex justify-center w-1/5 text-lg">{item}</div>
            ))}
        </div>
    );

}