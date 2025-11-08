export default function DeliveryOptionsCZ() {
    return (
        <div className="flex flex-col items-start justify-start p-4">
            <h1 style={{fontSize: '28px', marginBottom: '16px'}}>Doručení a platba pro Česko</h1>
            <table style={{width: '100%', borderCollapse: 'collapse', textAlign: 'left'}}>
                <thead style={{backgroundColor: '#f5f5f5'}}>
                <tr>
                    <th style={{padding: '8px', borderBottom: '1px solid #ccc'}}>Možnosti doručení</th>
                    <th style={{padding: '8px', borderBottom: '1px solid #ccc'}}>Cena dopravy</th>
                    <th style={{padding: '8px', borderBottom: '1px solid #ccc'}}>Dodací lhůta</th>
                    <th style={{padding: '8px', borderBottom: '1px solid #ccc'}}>Způsob platby</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td style={{padding: '8px', borderBottom: '1px solid #eee'}}>
                        Zásilkovna (Packeta) parcelshop
                    </td>
                    <td style={{padding: '8px', borderBottom: '1px solid #eee'}}>
                        89 Kč<br/>
                    </td>
                    <td style={{padding: '8px', borderBottom: '1px solid #eee'}}>
                        během 1-2 pracovních dnů<br/>
                        objednávky do výše 20 000 Kč
                    </td>
                    <td style={{padding: '8px', borderBottom: '1px solid #eee'}}>
                        online kartou<br/>
                    </td>
                </tr>
                </tbody>
            </table>
            <h2 style={{fontSize: '24px', marginTop: '16px'}}>Platba kartou online</h2>
            <p style={{fontSize: '18px', marginTop: '6px'}}>Cena: zdarma</p>
            <p style={{fontSize: '18px', marginTop: '6px'}}>Okamžitá platba</p>
        </div>
            )
            ;
            }
