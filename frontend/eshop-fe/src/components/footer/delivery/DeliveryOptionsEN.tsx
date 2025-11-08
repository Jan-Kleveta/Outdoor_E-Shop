export default function DeliveryOptionsEN() {
    return (
        <div className="flex flex-col items-start justify-start p-4">
            <h1 style={{fontSize: '28px', marginBottom: '16px'}}>Czechia payment and delivery</h1>
            <table style={{width: '100%', borderCollapse: 'collapse', textAlign: 'left'}}>
                <thead style={{backgroundColor: '#f5f5f5'}}>
                <tr>
                    <th style={{padding: '8px', borderBottom: '1px solid #ccc'}}>Delivery options</th>
                    <th style={{padding: '8px', borderBottom: '1px solid #ccc'}}>Shipping price</th>
                    <th style={{padding: '8px', borderBottom: '1px solid #ccc'}}>Delivery time</th>
                    <th style={{padding: '8px', borderBottom: '1px solid #ccc'}}>Payment method</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td style={{padding: '8px', borderBottom: '1px solid #eee'}}>
                        Packeta parcelshop
                    </td>
                    <td style={{padding: '8px', borderBottom: '1px solid #eee'}}>
                        €3.60
                    </td>
                    <td style={{padding: '8px', borderBottom: '1px solid #eee'}}>
                        within 1–2 workdays<br/>
                        orders up to €820
                    </td>
                    <td style={{padding: '8px', borderBottom: '1px solid #eee'}}>
                        online by card<br/>
                    </td>
                </tr>
                </tbody>
            </table>
            <h2 style={{fontSize: '24px', marginTop: '16px'}}>Card payment online</h2>
            <p style={{fontSize: '18px', marginTop: '6px'}}>Price: free of charge</p>
            <p style={{fontSize: '18px', marginTop: '6px'}}>Instant payment</p>
        </div>
            );
            }
