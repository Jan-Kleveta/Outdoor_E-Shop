export async function getProducts() {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/stock/v1/public/product`, {
        cache: "no-store",
    });


    if (!res.ok) {
        const errorResponse = await res.json().catch(() => null);
        console.error("API Error:", errorResponse || res.statusText);
        throw new Error(errorResponse?.message || "Failed to fetch products");
    }

    return res.json();
}
