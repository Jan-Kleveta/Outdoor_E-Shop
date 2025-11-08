
import HeroImage from "@/components/image/HeroImage";
import ProductGrid from "@/components/product/ProductGrid";
import {getProducts} from "@/app/lib/getProducts";




export default async function HomePage() {
    const products = await getProducts();

    return (
        <>
            <main>
                <HeroImage
                    imageUrl={`${process.env.NEXT_PUBLIC_API_URL}/stock/v1/public/images/uvod5.JPG`}
                    height={"900px"}
                    headline={"Premium ultralight wear made in Czech Republic"}
                    loading="lazy"
                />

                {products.length > 0 ? (
                    <ProductGrid products={products} />
                ) : (
                    <p className="text-center text-red-500 mt-10">
                        Sorry, we could not load the products. Try again later.
                    </p>
                )}

                <HeroImage
                    imageUrl={`${process.env.NEXT_PUBLIC_API_URL}/stock/v1/public/images/footer3.JPG`}
                    height={"600px"}
                    mt={"mt-24"}
                    loading="lazy"
                />
            </main>
        </>
    );
}
