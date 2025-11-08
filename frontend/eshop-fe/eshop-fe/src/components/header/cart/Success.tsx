interface SuccessPageProps {
    params: { orderId: string };
}

export default function Success({ params }: SuccessPageProps) {
    const { orderId } = params;

    return (
        <div className="max-w-xl mx-auto p-6 text-center">
            <h1 className="text-2xl font-bold mb-4">Thank you for your order!</h1>
            <p>Your order <strong>{orderId}</strong> has been successfully processed.</p>
            <p className="mt-4">You'll receive an email with order confirmation within two working days.</p>
        </div>
    );
}
