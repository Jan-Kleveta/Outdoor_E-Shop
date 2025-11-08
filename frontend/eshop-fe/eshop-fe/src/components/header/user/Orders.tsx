"use client";

import { useUserOrdersQuery } from "@/app/hooks/useUserOrdersQuery";

const UserOrders = () => {
    const { data, isLoading, isError, error } = useUserOrdersQuery();

    if (isLoading) return <p>Loading orders…</p>;
    if (isError) return <p>Error: {(error as Error).message}</p>;

    return (
        <div className="w-[800px]">
            <section
                style={{
                    width: '100%',
                    backgroundColor: 'white',
                    height: '200px',
                }}
            ></section>
            <h2 className="text-2xl font-bold mb-6">My Orders</h2>

            {data?.map((order) => (
                <div
                    key={order.orderNumber}
                    className="border border-gray-300 rounded p-6 mb-8 space-y-6 w-full"
                >
                    <div className="flex flex-col md:flex-row gap-8">
                        <div className="flex-1 space-y-1">
                            <h3 className="text-lg font-semibold">Order Details</h3>
                            <p><strong>Order number:</strong> {order.orderNumber}</p>
                            <p><strong>Status:</strong> {order.status}</p>
                            <p><strong>Date:</strong> {order.timestamp || "–"}</p>
                            <p><strong>Email:</strong> {order.email}</p>
                        </div>

                        <div className="flex-1 space-y-1">
                            <h3 className="text-lg font-semibold">Shipping Address</h3>
                            <p>{order.shippingAddressResponse.name}</p>
                            <p>{order.shippingAddressResponse.addressLine1}</p>
                            {order.shippingAddressResponse.addressLine2 && (
                                <p>{order.shippingAddressResponse.addressLine2}</p>
                            )}
                            <p>
                                {order.shippingAddressResponse.zipCode},{" "}
                                {order.shippingAddressResponse.city},{" "}
                                {order.shippingAddressResponse.country}
                            </p>
                            <p>Phone: {order.shippingAddressResponse.phoneNumber?.localNumber}</p>
                        </div>

                        <div className="flex-1 space-y-1">
                            <h3 className="text-lg font-semibold">Billing Address</h3>
                            <p>{order.billingAddressResponse.name}</p>
                            <p>{order.billingAddressResponse.addressLine1}</p>
                            {order.billingAddressResponse.addressLine2 && (
                                <p>{order.billingAddressResponse.addressLine2}</p>
                            )}
                            <p>
                                {order.billingAddressResponse.zipCode},{" "}
                                {order.billingAddressResponse.city},{" "}
                                {order.billingAddressResponse.country}
                            </p>
                        </div>
                    </div>

                    <div>
                        <h4 className="text-md font-semibold mb-2">Products</h4>
                        <ul className="space-y-2">
                            {order.productInstanceList.map((item, idx) => (
                                <li
                                    key={idx}
                                    className="border border-gray-200 rounded p-3 text-sm"
                                >
                                    <p><strong>{item.productName}</strong></p>
                                    <p>{item.orderedQuantity} × {item.pricePerUnit} {item.currency}</p>
                                </li>
                            ))}
                        </ul>
                    </div>
                    <div>
                        <p><strong>Total:</strong> {order.totalPrice} {order.currency}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default UserOrders;
