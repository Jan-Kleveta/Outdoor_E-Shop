import { notFound } from "next/navigation";
import ProductPageClient from "@/app/product/[id]/ProductPageClient";

async function getProduct(id: string) {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/stock/v1/public/product/${id}`, {
        cache: "no-store",
    });

    if (!res.ok) return null;
    return res.json();
}

export default async function ProductPage({ params }: { params: { id: string } }) {
    const product = await getProduct(params.id);
    if (!product) return notFound();

    return <ProductPageClient product={product} />;
}
