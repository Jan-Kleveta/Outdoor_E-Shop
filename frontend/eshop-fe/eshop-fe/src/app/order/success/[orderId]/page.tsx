import Success from "@/components/header/cart/Success";

interface PageProps {
    params: { orderId: string };
}

export default function SuccessPage({ params }: PageProps) {
    return (
        <div className="min-h-screen bg-white text-black p-4">
            <section className="w-full h-[200px] bg-white"></section>
            <div className="max-w-7xl mx-auto">
                <Success params={params} />
            </div>
        </div>
    );
}
