import { useState } from "react";
import { Product } from "@/types/product";
import { useCartStore } from "@/store/cartStore";

type ProductControlProps = {
    product: Product;
};

export default function ProductControls({ product }: ProductControlProps) {
    const { addToCart } = useCartStore();
    const price = product.prices[0]?.price ?? "N/A";
    const currency = product.prices[0]?.currency ?? "";

    const [selectedSize, setSelectedSize] = useState(product.productsWithSize[0] ?? null);
    const [quantity, setQuantity] = useState(1);
    const [addedToCart, setAddedToCart] = useState(false);

    const handleSizeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const selectedId = parseInt(e.target.value);
        const found = product.productsWithSize.find((s) => s.id === selectedId);
        if (found) {
            setSelectedSize(found);
        }
    };

    const handleAddToCart = () => {
        if (!selectedSize || quantity <= 0) return;

        addToCart({
            productWithSizeId: selectedSize.id,
            productId: product.id,
            size: selectedSize.size,
            quantity: quantity,
            name: product.name,
            price: price,
            currency: currency,
            image: product.productPhotos[0]?.path
                ? `http://localhost:8088${product.productPhotos[0].path}`
                : "/placeholder.jpg",
        });

        setAddedToCart(true);
        setTimeout(() => {
            setAddedToCart(false);
        }, 1500);
    };

    return (
        <div className="flex flex-col items-end text-right w-1/2 space-y-4 pt-10">
            <h1 className="text-3xl font-bold">{product.name}</h1>
            <p className="text-2xl font-semibold">
                {price} {currency}
            </p>

            {selectedSize ? (
                <p className="text-gray-600">
                    Weight: {selectedSize.weight}g ({selectedSize.size})
                </p>
            ) : (
                <p className="text-gray-600">Not available</p>
            )}

            <p className="text-gray-500">{product.description}</p>

            <div className="flex flex-col gap-4 font-bold">
                <label>
                    Sizes:
                    {product.productsWithSize.length > 0 ? (
                        <select
                            className="w-full border p-2 rounded mt-1"
                            onChange={handleSizeChange}
                            value={selectedSize?.id ?? ""}
                            disabled={product.productsWithSize.length === 0}
                        >
                            {product.productsWithSize.map((s) => (
                                <option key={s.id} value={s.id}>
                                    {s.size}
                                </option>
                            ))}
                        </select>
                    ) : (
                        <p className="text-red-500 mt-2">Out of stock</p>
                    )}
                </label>

                {product.productsWithSize.length > 0 && (
                    <p className={selectedSize && selectedSize.stockQuantity > 0 ? "text-green-600" : "text-red-600"}>
                        {selectedSize && selectedSize.stockQuantity > 0 ? "In stock" : "Out of stock"}
                    </p>
                )}

                <div className="flex items-center gap-4">
                    <button
                        className={`border px-4 py-2 rounded hover:bg-gray-100 ${
                            addedToCart ? "bg-green-500 text-white" : ""
                        }`}
                        disabled={!selectedSize || selectedSize.stockQuantity === 0}
                        onClick={handleAddToCart}
                    >
                        {addedToCart ? "✔ Added!" : "Add to cart"}
                    </button>

                    <input
                        type="number"
                        min={1}
                        max={3}
                        value={quantity}
                        onChange={(e) => setQuantity(Number(e.target.value))}
                        className="w-16 border px-2 py-1 rounded text-center"
                        disabled={!selectedSize}
                    />
                </div>
            </div>

            <div className="mt-6 space-y-2 w-32/60">
                <details className="border p-2 rounded w-full">
                    <summary className="cursor-pointer font-medium">FABRIC</summary>
                    <p className="text-sm mt-2">Polartec Alpha Direct 90gsm</p>
                </details>
                <details className="border p-2 rounded w-full">
                    <summary className="cursor-pointer font-medium">SIZE CHART</summary>
                    <p className="text-sm mt-2">Velikosti jsou evropské, standardní.</p>
                </details>
            </div>
        </div>
    );
}
