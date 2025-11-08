'use client';

import { useState, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import { useLoginMutation } from '@/app/hooks/useLoginMutation';
import Link from "next/link";
import { Eye, EyeOff } from 'lucide-react';

export default function LoginForm() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [emailError, setEmailError] = useState('');

    const router = useRouter();
    const { mutate: login, isPending, isError, error } = useLoginMutation();

    const validateEmail = (email: string) => {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!regex.test(email)) {
            setEmailError("Invalid email format");
            return false;
        }
        setEmailError("");
        return true;
    };

    const handleSubmit = useCallback((e: React.FormEvent) => {
        e.preventDefault();

        if (!validateEmail(email)) return;

        login(
            { email, password },
            {
                onSuccess: () => {
                    router.push('/');
                },
            }
        );
    }, [email, password, login, router]);

    return (
        <form onSubmit={handleSubmit} className="max-w-md mx-auto space-y-4 p-6 border rounded">
            <h2 className="text-xl font-bold text-center">Login</h2>

            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Email"
                required
                className={`w-full border p-2 rounded ${emailError ? 'border-red-500' : ''}`}
            />

            {emailError && (
                <p className="text-red-500 text-sm">{emailError}</p>
            )}

            <div className="relative">
                <input
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    required
                    className="w-full border p-2 rounded"
                />
                <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-black"
                >
                    {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
                </button>
            </div>

            {isError && (
                <div className="text-red-500 text-sm p-2 border border-red-400 rounded bg-red-50">
                    {(error as Error)?.message || 'Something went wrong'}
                </div>
            )}

            <button
                type="submit"
                className="w-full bg-black text-white p-2 rounded disabled:opacity-50 flex items-center justify-center gap-2"
                disabled={isPending}
            >
                {isPending ? (
                    <>
                        <span className="loader animate-spin w-4 h-4 border-2 border-white border-t-transparent rounded-full" />
                        Logging in...
                    </>
                ) : (
                    'Login'
                )}
            </button>

            <Link
                href="/user/register"
                className="block w-full bg-white text-black border border-black p-2 rounded text-center hover:bg-gray-100 transition"
            >
                Register
            </Link>
        </form>
    );
}
