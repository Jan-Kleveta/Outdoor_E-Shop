'use client';

import { useAuthStore } from '@/store/authStore';
import { usePathname } from 'next/navigation';
import Link from 'next/link';
import { useRouter } from 'next/navigation';


export default function Header() {
    const pathname = usePathname();
    const router = useRouter();
    const isHome = pathname === '/';
    const isProfile = pathname === '/user/profile';

    const { isLoggedIn, logout } = useAuthStore();

    const handleLogout = () => {
        logout();
        router.push('/');
    };

    return (
        <header
            className={`absolute top-0 left-0 w-full z-50 bg-transparent font-bold ${
                isHome ? 'text-white' : 'text-black'
            }`}
        >
            <div className="grid grid-cols-3 items-center h-20 px-[10%]">
                <nav className="space-x-6 text-lg">
                    <Link href="/info/learn">Learn</Link>
                    <Link href="/info/about">About</Link>
                </nav>

                <div className="text-4xl flex justify-center tracking-wide">
                    <Link href="/">Outdoorio</Link>
                </div>

                <div className="flex justify-end space-x-6 text-lg">
                    <Link href="/user/cart">Cart</Link>

                    {isLoggedIn ? (
                        isProfile ? (
                            <button onClick={handleLogout} className="cursor-pointer">Logout</button>
                        ) : (
                            <Link href="/user/profile">User</Link>
                        )
                    ) : (
                        <Link href="/user/login">Login</Link>
                    )}
                </div>
            </div>
        </header>
    );
}
