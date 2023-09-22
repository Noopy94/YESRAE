
interface ICategoryProps{
    categories: string[];
}

export default function Category({
    categories
}: ICategoryProps){
    return(
        <div className="flex w-full text-lg mb-7">
            {categories.map((item, idx) => (
                <div key = {idx} className="mx-16">{item}</div>
            ))}
        </div>
    );

}