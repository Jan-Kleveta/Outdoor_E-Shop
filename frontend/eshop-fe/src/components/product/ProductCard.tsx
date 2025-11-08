

type ProductProps = {
    name: string;
    price: string;
    inStock: boolean;
    image: string;
};


export default function ProductCard({ name, price, inStock, image }: ProductProps) {
    return (
        <div className="p-4 rounded shadow hover:shadow-lg transition font-bold font-logo">
            <img src={image} alt={name} className="w-full aspect-[4/5] object-cover mb-4" />
            <h2 className="text-xl ">{name}</h2>
            <p>{price}</p>
            <p className={inStock ? "text-green-600" : "text-red-600"}>
                {inStock ? "In stock" : "Out of stock"}
            </p>
        </div>
    );
}
