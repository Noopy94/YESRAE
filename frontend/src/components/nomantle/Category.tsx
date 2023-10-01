interface ICategoryProps {
  categories: string[];
}

export default function Category({ categories }: ICategoryProps) {
  return (
    <div className="flex items-center w-10/12 text-center mb-7">
      {categories.map((item, idx) => (
        <div key={idx} className="flex justify-center w-1/5 text-xl font-bold">
          {item}
        </div>
      ))}
    </div>
  );
}
