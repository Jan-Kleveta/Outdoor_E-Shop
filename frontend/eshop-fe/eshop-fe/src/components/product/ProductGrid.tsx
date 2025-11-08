import Link from "next/link";
import ProductCard from "./ProductCard";
import {Product} from "@/types/product";

type ProductGridProps = {
    products: Product[];
};

export default function ProductGrid({ products }: ProductGridProps) {
    return (
        <section className="max-w-7xl mx-auto px-4 py-8 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6">
            {products.map((product) => {
                const price = product.prices?.[0]?.price ?? "N/A";
                const currency = product.prices?.[0]?.currency ?? "";
                const imagePath = product.productPhotos?.[0]?.path;
                const image = imagePath
                    ? `http://localhost:8088${imagePath}`
                    : "/placeholder.jpg";

                const totalStock = (product.productsWithSize ?? []).reduce(
                    (sum, pws) => sum + (pws.stockQuantity || 0),
                    0
                );
                const inStock = totalStock > 0;

                return (
                    <Link key={product.id} href={`/product/${product.id}`}>
                        <ProductCard
                            name={product.name}
                            price={`${price} ${currency}`}
                            inStock={inStock}
                            image={image}
                        />
                    </Link>
                );
            })}
        </section>
    );
}
